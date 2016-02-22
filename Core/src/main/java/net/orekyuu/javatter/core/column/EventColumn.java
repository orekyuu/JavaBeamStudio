package net.orekyuu.javatter.core.column;

import com.gs.collections.api.list.ImmutableList;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import net.orekyuu.javatter.api.column.Column;
import net.orekyuu.javatter.api.column.ColumnController;
import net.orekyuu.javatter.api.column.ColumnService;
import net.orekyuu.javatter.api.column.ColumnState;
import net.orekyuu.javatter.api.service.TwitterUserService;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.DirectMessage;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.userstream.OnMention;
import net.orekyuu.javatter.api.twitter.userstream.events.OnDirectMessage;
import net.orekyuu.javatter.api.twitter.userstream.events.OnException;
import net.orekyuu.javatter.api.twitter.userstream.events.OnFavorite;
import net.orekyuu.javatter.api.twitter.userstream.events.OnFollow;
import net.orekyuu.javatter.core.service.PluginServiceImpl;

import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EventColumn implements ColumnController, Initializable,
        OnMention, OnFavorite, OnFollow, OnDirectMessage, OnException {

    public static final String ID = "event";
    private static final String KEY = "userId";

    @FXML
    private VBox root;
    @FXML
    private MenuButton accountSelect;
    @FXML
    private ListView<EventValue> event;
    @FXML
    private Label title;
    private ColumnState columnState;
    private ObjectProperty<TwitterUser> owner = new SimpleObjectProperty<>();

    private Optional<TwitterUser> user;
    @Inject
    private TwitterUserService userService;
    @Inject
    private ColumnService columnService;

    @Override
    public void restoration(ColumnState columnState) {
        this.columnState = columnState;
        String userName = (String) columnState.getData(KEY).getFirst();
        if (userName == null) {
            user = userService.selectedAccount();
        } else {
            user = userService.findTwitterUser(userName);
        }

        user.ifPresent(twitterUser -> twitterUser.userStream()
                .onMention(this)
                .onFavorite(this)
                .onDirectMessage(this)
                .onException(this)
                .onFollow(this)
        );

        user.ifPresent(twitterUser -> {
            title.setText(twitterUser.getUser().getScreenName());
            this.columnState.setData(KEY, twitterUser.getUser().getScreenName());
        });

        owner.set(user.orElse(null));
        Runnable runnable = () -> user
                .map(u -> u.getUser().getScreenName())
                .map(name -> "@" + name)
                .ifPresent(title::setText);
        Platform.runLater(runnable);

    }

    @Override
    public void onClose() {
        user.ifPresent(this::disposeUserStream);
    }

    @Override
    public String getPluginId() {
        return PluginServiceImpl.BUILD_IN.getPluginId();
    }

    @Override
    public String getColumnId() {
        return EventColumn.ID;
    }

    @Override
    public ColumnState getState() {
        return columnState;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImmutableList<TwitterUser> twitterUsers = userService.allUser();
        for (TwitterUser twitterUser : twitterUsers) {
            MenuItem item = new MenuItem(twitterUser.getUser().getScreenName());
            item.setOnAction(e -> {
                user.ifPresent(this::disposeUserStream);

                user = Optional.of(twitterUser);
                columnState.setData(KEY, twitterUser.getUser().getScreenName());
                title.setText(twitterUser.getUser().getScreenName());
                event.getItems().clear();
                owner.set(user.orElse(null));
            });
            accountSelect.getItems().add(item);
        }

        event.setCellFactory(new Callback<ListView<EventValue>, ListCell<EventValue>>() {
            @Override
            public ListCell<EventValue> call(ListView<EventValue> param) {
                return new ListCell<EventValue>() {
                    @Override
                    protected void updateItem(EventValue item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setGraphic(null);
                            return;
                        }


                        VBox root = new VBox();
                        //タイトル
                        HBox title = new HBox();
                        Pane spacer = new Pane();
                        HBox.setHgrow(spacer, Priority.ALWAYS);
                        title.getChildren().addAll(
                                new Label(item.getTitle()),
                                spacer,
                                new Label(item.getCreatedAt())
                        );
                        root.getChildren().add(title);
                        //コンテンツ
                        String content = Arrays.stream(item.getLines()).collect(Collectors.joining("\n"));
                        root.getChildren().add(new TextFlow(new Text(content)));

                        //レイアウト
                        DoubleBinding binding = Bindings.add(-20.0, getListView().widthProperty());
                        root.prefWidthProperty().bind(binding);
                        root.maxWidthProperty().bind(binding);
                        root.prefWidth(getWidth());
                        root.maxWidth(getWidth());
                        setGraphic(root);
                    }
                };
            }
        });
    }

    private void disposeUserStream(TwitterUser user) {
        user.userStream()
                .removeOnMention(this)
                .removeOnFavorite(this)
                .removeOnDirectMessage(this)
                .removeOnException(this)
                .removeOnFollow(this);
    }

    public void closeRequest(ActionEvent actionEvent) {
        user.ifPresent(this::disposeUserStream);
        columnService.removeColumn(new Column(this, root));
    }

    @Override
    public void onMention(Tweet tweet, User from) {
        putText(String.format("%s(@%s) からメンション", from.getName(), from.getScreenName()), tweet.getText());
    }

    @Override
    public void onFavorite(User source, User target, Tweet tweet) {
        putText(String.format("%s(@%s) にふぁぼられました", source.getName(), source.getScreenName()), tweet.getText());

    }

    @Override
    public void onDirectMessage(DirectMessage directMessage) {
        putText("ダイレクトメッセージ",
                String.format("from: %s(@%s)", directMessage.getRecipient().getName(), directMessage.getRecipient().getScreenName()),
                "message: ",
                directMessage.getText()
        );
    }

    @Override
    public void onException(Exception e) {
        putText("例外が発生", "クラス名: " + e.getClass().getName(), "メッセージ: " + e.getMessage());
    }

    @Override
    public void onFollow(User source, User followedUser) {
        putText("フォロー", String.format("%s(@%s) にフォローされました", source.getName(), source.getScreenName()));
    }

    private void putText(String title, String ... lines) {

        EventValue eventValue = new EventValue(title, lines, LocalDateTime.now()
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        Platform.runLater(() -> event.getItems().add(0, eventValue));
    }

    private static class EventValue {
        private String title;
        private String[] lines;
        private String createdAt;

        public EventValue(String title, String[] lines, String createdAt) {
            this.title = title;
            this.lines = lines;
            this.createdAt = createdAt;
        }

        public String getTitle() {
            return title;
        }

        public String[] getLines() {
            return lines;
        }

        public String getCreatedAt() {
            return createdAt;
        }
    }

}
