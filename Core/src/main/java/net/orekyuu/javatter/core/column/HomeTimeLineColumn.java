package net.orekyuu.javatter.core.column;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.orekyuu.javatter.api.column.ColumnController;
import net.orekyuu.javatter.api.column.ColumnState;
import net.orekyuu.javatter.api.service.TwitterUserService;
import net.orekyuu.javatter.api.twitter.TwitterUser;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomeTimeLineColumn implements ColumnController, Initializable {
    public static final String ID = "timeline";

    private static final String KEY = "userId";
    @FXML
    private ListView timeline;
    @FXML
    private Label title;

    private TwitterUser user;
    private static final Logger logger = Logger.getLogger(HomeTimeLineColumn.class.getName());

    @Inject
    private TwitterUserService userService;

    @Override
    public void restoration(ColumnState columnState) {
        if (columnState == null) {
            columnState = newColumnState();
        }
        String userName = (String) columnState.getData(KEY).getFirst();
        userService.findTwitterUser(userName).ifPresent(user -> this.user = user);
        if (user == null) {
            user = userService.selectedAccount().get();
        }

        Runnable runnable = () -> title.setText("@" + user.getUser().getScreenName());
        Platform.runLater(runnable);
        user.userStream().onStatus(tweet -> Platform.runLater(() -> timeline.getItems().add(tweet.getText())));
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
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("initialize");
    }
}
