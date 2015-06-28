package net.orekyuu.javatter.core.column;

import javafx.fxml.Initializable;
import net.orekyuu.javatter.api.column.Column;
import net.orekyuu.javatter.api.column.ColumnState;
import net.orekyuu.javatter.api.service.TwitterUserService;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.util.lookup.Lookup;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomeTimeLineColumn implements Column, Initializable {
    public static final String ID = "timeline";

    private static final String KEY = "userId";

    private TwitterUser user;
    private static final Logger logger = Logger.getLogger(HomeTimeLineColumn.class.getName());

    @Override
    public void restoration(ColumnState columnState) {
        if (columnState == null) {
            columnState = newColumnState();
        }
        String userName = (String) columnState.getData(KEY).getFirst();
        TwitterUserService userService = Lookup.lookup(TwitterUserService.class);
        userService.findTwitterUser(userName).ifPresent(user -> this.user = user);
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
