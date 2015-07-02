package net.orekyuu.javatter.core.column;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import net.orekyuu.javatter.api.column.ColumnController;
import net.orekyuu.javatter.api.column.ColumnState;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.service.TwitterUserService;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.userstream.events.OnStatus;
import net.orekyuu.javatter.core.control.TweetCellController;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomeTimeLineColumn implements ColumnController, Initializable {
    public static final String ID = "timeline";

    private static final String KEY = "userId";
    @FXML
    private ListView<Tweet> timeline;
    @FXML
    private Label title;

    private Optional<TwitterUser> user;
    private static final Logger logger = Logger.getLogger(HomeTimeLineColumn.class.getName());

    @Inject
    private TwitterUserService userService;
    //弱参照でリスナが保持されるためフィールドに束縛しておく
    private OnStatus onStatus = this::onStatus;

    @Override
    public void restoration(ColumnState columnState) {
        if (columnState == null) {
            columnState = newColumnState();
        }
        logger.info(columnState.toString());
        String userName = (String) columnState.getData(KEY).getFirst();
        if (userName == null) {
            user = userService.selectedAccount();
        } else {
            user = userService.findTwitterUser(userName);
        }


        Runnable runnable = () -> user
                .map(u -> u.getUser().getScreenName())
                .map(name -> "@" + name)
                .ifPresent(title::setText);
        Platform.runLater(runnable);
        user.ifPresent(user -> user.userStream().onStatus(onStatus));
    }

    private void onStatus(Tweet tweet) {
        Platform.runLater(() -> {
            logger.info("onStatus: " + tweet.getText());
            timeline.getItems().add(0, tweet);
        });
    }

    @Override
    public String getPluginId() {
        return ColumnInfos.PLUGIN_ID_BUILDIN;
    }

    @Override
    public String getColumnId() {
        return HomeTimeLineColumn.ID;
    }

    @Override
    public void onClose(ColumnState columnState) {
        logger.info("save");
        user.map(TwitterUser::getUser).ifPresent(u -> columnState.setData(KEY, u.getScreenName()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
        timeline.setCellFactory(param -> {
            try {
                return new TweetCell();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    private class TweetCell extends ListCell<Tweet> {

        private Parent parent;
        private TweetCellController controller;
        private ObjectProperty<Tweet> tweet = new SimpleObjectProperty<>();

        public TweetCell() throws IOException {
            JavatterFXMLLoader loader = new JavatterFXMLLoader(getClass().getResource("/layout/tweetCell.fxml"));
            parent = loader.load();
            controller = loader.getController();
            controller.tweetProperty().bind(tweet);
        }

        @Override
        protected void updateItem(Tweet item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || tweet == null) {
                setGraphic(null);
            } else {
                tweet.setValue(item);
                if (getGraphic() == null) {
                    setGraphic(parent);
                }
            }
        }
    }
}
