package net.orekyuu.javatter.core.controller;

import com.gs.collections.api.list.ImmutableList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.orekyuu.javatter.api.account.AccountStorageService;
import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.action.ActionManager;
import net.orekyuu.javatter.api.action.MenuAction;
import net.orekyuu.javatter.api.action.MenuActionEvent;
import net.orekyuu.javatter.api.column.*;
import net.orekyuu.javatter.api.command.CommandManager;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.controller.OwnerStage;
import net.orekyuu.javatter.api.service.ApplicationService;
import net.orekyuu.javatter.api.service.MainTweetAreaService;
import net.orekyuu.javatter.api.service.TwitterUserService;
import net.orekyuu.javatter.api.storage.DataStorageService;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.util.lookup.Lookup;
import net.orekyuu.javatter.core.column.HomeTimeLineColumn;
import net.orekyuu.javatter.core.control.AboutPopup;
import net.orekyuu.javatter.core.control.AccountSelection;
import net.orekyuu.javatter.core.service.PluginServiceImpl;
import net.orekyuu.javatter.core.settings.storage.GeneralSetting;

import javax.inject.Inject;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {

    @FXML
    private Menu actionsMenu;
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
    private MainTweetAreaService mainTweetAreaService;
    @Inject
    private CommandManager commandManager;
    @Inject
    private DataStorageService storageService;

    private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());

    private AccountSelection accountSelection;
    private AboutPopup aboutPopup = new AboutPopup();

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
                .setImage(new Image(user.getUser().getOriginalProfileImageURL(), true)));
        //アカウント変更時に画像を変更する
        twitterUserService.selectedAccountProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                account.setImage(null);
            } else {
                account.setImage(new Image(newValue.getUser().getOriginalProfileImageURL(), true));
            }
        });

        for (ColumnManager.ColumnInfo info : columnManager.getAllColumnInfo()) {
            //if (info.getPluginId().equals(PluginServiceImpl.BUILD_IN.getPluginId())) {
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
            //}
        }

        //アクションの登録
        ActionManager actionManager = Lookup.lookup(ActionManager.class);
        ImmutableList<MenuAction> actions = actionManager.getMenuActions();
        ObservableList<MenuItem> items = actionsMenu.getItems();
        for (MenuAction action : actions) {
            MenuItem e = new MenuItem(action.getActionName());
            KeyCombination defaultShortcut = action.getDefaultShortcut();
            if (defaultShortcut != null) {
                e.setAccelerator(defaultShortcut);
            }
            e.setOnAction(event -> action.getEvent().action(new MenuActionEvent()));
            items.add(e);
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

        mainTweetAreaService.addChangeTextListener(((oldValue, newValue) -> {
            Runnable runnable = () -> tweetInput.setText(newValue);
            if (Platform.isFxApplicationThread()) {
                runnable.run();
            } else {
                Platform.runLater(runnable);
            }
        }));
        tweetInput.textProperty().addListener((observable, oldValue, newValue) -> {
            mainTweetAreaService.setText(newValue);
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
            ColumnState columnState = c.getState();
            c.onClose();
            if (columnState == null) {
                columnState = c.defaultColumnState();
            }
            return columnState;
        });
        columnStateStorageService.save(states);
        //ユーザーの後処理
        //後処理に時間かかるのでバックグラウンドでやらせる
        new Thread(() -> {
            twitterUserService.allUser().each(TwitterUser::dispose);
        }).start();
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
        if (accountSelection != null) {
            accountSelection.show(root, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        }
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
        aboutPopup.show(root);
    }

    public void addHomeColumn() throws IOException {
        Optional<ColumnFactory> factory = columnManager.findByPluginIdAndColumnId(PluginServiceImpl.BUILD_IN.getPluginId(),
                HomeTimeLineColumn.ID);
        Column pair = factory.get().newInstance(null);
        columnService.addColumn(pair);
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

    public void onImageDrop(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        Pattern pattern = Pattern.compile("\\.(png|jpg|gif)$");
        if (dragboard.hasFiles()) {
            List<File> first = dragboard.getFiles().stream()
                    .filter(file -> pattern.matcher(file.getPath()).find())
                    .collect(Collectors.toList());
            first.forEach(mainTweetAreaService::addMedia);
            event.setDropCompleted(!first.isEmpty());
        }
        event.consume();

    }

    public void onImageDragOver(DragEvent event) {
        Dragboard dragboard=event.getDragboard();
        if (dragboard.hasFiles()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    public void onTweet() {
        DataStorageService storageService = Lookup.lookup(DataStorageService.class);
        MainTweetAreaService mainTweetAreaService = Lookup.lookup(MainTweetAreaService.class);
        GeneralSetting setting = storageService
                .find(PluginServiceImpl.BUILD_IN.getPluginId(), GeneralSetting.class, new GeneralSetting());
        if (setting.isCheckTweet()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "ツイートしますか？", ButtonType.YES, ButtonType.NO);
            alert.setTitle("確認");
            Optional<ButtonType> result = alert.showAndWait();
            result.filter(t -> t == ButtonType.YES).ifPresent(t -> mainTweetAreaService.tweet());
        } else {
            mainTweetAreaService.tweet();
        }
    }

    public void exit() {
        ApplicationService service = Lookup.lookup(ApplicationService.class);
        try {
            service.getApplication().stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void openPluginDir() {
        try {
            Desktop.getDesktop().open(new File("plugins"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
