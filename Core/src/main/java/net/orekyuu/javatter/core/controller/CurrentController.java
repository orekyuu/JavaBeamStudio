package net.orekyuu.javatter.core.controller;

import com.gs.collections.api.list.ImmutableList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.column.Column;
import net.orekyuu.javatter.api.column.ColumnFactory;
import net.orekyuu.javatter.api.column.ColumnState;
import net.orekyuu.javatter.api.command.CommandManager;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.controller.OwnerStage;
import net.orekyuu.javatter.api.service.*;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.core.column.ColumnInfos;
import net.orekyuu.javatter.core.column.HomeTimeLineColumn;
import net.orekyuu.javatter.core.control.AccountSelection;
import net.orekyuu.javatter.core.settings.storage.GeneralSetting;
import net.orekyuu.javatter.core.settings.storage.SettingsStorage;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CurrentController implements Initializable {

    @FXML
    private Menu columnSelector;
    @FXML
    private TextArea tweetInput;
    @FXML
    private VBox root;
    @FXML
    private ImageView account;
    @FXML
    private HBox columns;

    @Inject
    private AccountStorageService accountStorageService;

    @Inject
    private TwitterUserService twitterUserService;
    @Inject
    private ColumnManager columnManager;
    @Inject
    private ColumnService columnService;
    @Inject
    private ColumnStateStorageService columnStateStorageService;
    @Inject
    private CurrentTweetAreaService currentTweetAreaService;
    @Inject
    private CommandManager commandManager;
    @Inject
    private SettingsStorage settingsStorage;

    private static final Logger logger = Logger.getLogger(CurrentController.class.getName());

    private AccountSelection accountSelection;

    @OwnerStage
    private Stage owner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //アカウントの初期化
        initAccount();
        //Windowを閉じた時にTwitterUserの後処理をする
        owner.setOnCloseRequest(this::onCloseRequest);

        account.setOnMouseClicked(this::showAccountList);

        //アカウントの画像を設定
        twitterUserService.selectedAccount().ifPresent(user -> account
                .setImage(new Image(user.getUser().getProfileImageURL(), true)));

        for (ColumnManager.ColumnInfo info : columnManager.getAllColumnInfo()) {
            if (info.getPluginId().equals(ColumnInfos.PLUGIN_ID_BUILDIN)) {
                MenuItem menuItem = new MenuItem(info.getName());
                menuItem.setOnAction(e -> {
                    Optional<ColumnFactory> factory = columnManager.findByPluginIdAndColumnId(info.getPluginId(),
                            info.getColumnName());
                    try {
                        Column pair = factory.get().newInstance(null);
                        columnService.addColumn(pair);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                columnSelector.getItems().add(menuItem);
            }
        }

        //カラムの追加のイベントを登録
        columnService.addColumnEvent(pair -> {
            Node root = pair.getRoot();
            HBox.setHgrow(root, Priority.ALWAYS);
            Runnable runnable = () -> columns.getChildren().add(root);
            if (!Platform.isFxApplicationThread()) {
                Platform.runLater(runnable);
            } else {
                runnable.run();
            }
        });
        columnService.removeColumnEvent(pair -> {
            Runnable runnable = () -> columns.getChildren().remove(pair.getRoot());
            if (!Platform.isFxApplicationThread()) {
                Platform.runLater(runnable);
            } else {
                runnable.run();
            }
        });

        //保存されていたカラムを復元する
        openSavedColumns();

        currentTweetAreaService.addChangeTextListener(((oldValue, newValue) -> {
            Runnable runnable = () -> tweetInput.setText(newValue);
            if (Platform.isFxApplicationThread()) {
                runnable.run();
            } else {
                Platform.runLater(runnable);
            }
        }));
        tweetInput.textProperty().addListener((observable, oldValue, newValue) -> {
            currentTweetAreaService.setText(newValue);
        });
    }

    private void openSavedColumns() {
        ImmutableList<ColumnState> states = columnStateStorageService.loadColumnStates();
        for (ColumnState state : states) {
            columnManager.findByPluginIdAndColumnId(state.getPluginId(), state.getColumnId())
                    .map(factory -> {
                        try {
                            return factory.newInstance(state);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    }).ifPresent(columnService::addColumn);
        }
    }

    //Windowのクローズリクエストイベント
    private void onCloseRequest(WindowEvent windowEvent) {
        //カラムの状態の保存
        ImmutableList<ColumnState> states = columnService.getAllColumn().collect(c -> {
            ColumnState columnState = c.newColumnState();
            c.onClose(columnState);
            return columnState;
        });
        columnStateStorageService.save(states);
        //ユーザーの後処理
        twitterUserService.allUser().each(TwitterUser::dispose);
    }

    private void initAccount() {
        //起動時にアカウント情報をチェックする
        checkAccount();

        //保存されてるTwitterアカウントを取得
        ImmutableList<TwitterAccount> twitterAccounts = accountStorageService.findByType(TwitterAccount.class);
        if (!twitterAccounts.isEmpty()) {
            //最初に見つけたユーザーを選択
            ImmutableList<TwitterUser> allUser = twitterUserService.allUser();
            TwitterUser first = allUser.getFirst();
            twitterUserService.select(first);

            //アカウントの選択ポップアップ
            accountSelection = new AccountSelection(allUser.toList(), twitterUserService::select);
        }
    }

    private void showAccountList(MouseEvent mouseEvent) {
        accountSelection.show(root, mouseEvent.getScreenX(), mouseEvent.getScreenY());
    }

    //アカウントが見つからなければアカウント設定を促す
    private void checkAccount() {
        ImmutableList<TwitterAccount> byType = accountStorageService.findByType(TwitterAccount.class);
        if (byType.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Twitterアカウントの認証設定を開きますか？", ButtonType.YES, ButtonType.NO);
            alert.setTitle("アカウント設定");
            alert.getDialogPane().setHeaderText("アカウントが見つかりません");
            Optional<ButtonType> buttonType = alert.showAndWait();
            buttonType.filter(t -> t == ButtonType.YES).ifPresent(t -> openAccountSettings());
        }
    }

    private void openAccountSettings() {
        JavatterFXMLLoader loader = new JavatterFXMLLoader(getClass().getResource("/layout/signup.fxml"));
        Stage stage = new Stage();
        loader.setOwnerStage(stage);
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.requestFocus();
            stage.initOwner(owner);
            stage.show();
            SignupController controller = loader.getController();
            stage.setOnCloseRequest(e -> controller.onClose());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openAbout() {
        JavatterFXMLLoader loader = new JavatterFXMLLoader(getClass().getResource("/layout/about.fxml"));
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        loader.setOwnerStage(stage);
        try {
            stage.setAlwaysOnTop(true);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.requestFocus();
            stage.initOwner(owner);
            stage.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    stage.close();
                }
            });
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTweet() throws IOException {
        GeneralSetting setting = settingsStorage.getGeneralSetting();
        if (setting.isCheckTweet()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "ツイートしますか？", ButtonType.YES, ButtonType.NO);
            alert.setTitle("確認");
            Optional<ButtonType> result = alert.showAndWait();
            result.filter(t -> t == ButtonType.YES).ifPresent(t -> currentTweetAreaService.tweet());
        } else {
            currentTweetAreaService.tweet();
        }
    }

    public void addHomeColumn() throws IOException {
        Optional<ColumnFactory> factory = columnManager.findByPluginIdAndColumnId(ColumnInfos.PLUGIN_ID_BUILDIN,
                HomeTimeLineColumn.ID);
        Column pair = factory.get().newInstance(null);
        columnService.addColumn(pair);
    }

    public void onExecCommand() {
        String text = currentTweetAreaService.getText();
        String s = commandManager.execCommand(currentTweetAreaService.getText());
        //コマンドによってテキストが書き換えられなかった場合のみ実行
        if (currentTweetAreaService.getText().equals(text)) {
            currentTweetAreaService.setText(s);
        }
    }

    public void openSettings() {
        JavatterFXMLLoader fxmlLoader = new JavatterFXMLLoader(getClass()
                .getResource("/layout/settings/settings.fxml"));
        fxmlLoader.setOwnerStage(owner);
        try {
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(owner);
            stage.setScene(scene);
            stage.setTitle("設定");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
