package net.orekyuu.javatter.core.controller;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.userwindow.UserWindowTab;
import net.orekyuu.javatter.core.control.UserCell;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserFollowController implements Initializable, UserWindowTab {
    public ListView<User> root;
    private ObjectProperty<TwitterUser> owner = new SimpleObjectProperty<>();

    @Override
    public void update(User user, TwitterUser owner) {
        owner.fetchFriends(user).thenAccept(result -> {
            Platform.runLater(() -> {
                root.getItems().setAll(result);
            });
        });
        this.owner.setValue(owner);
    }

    @Override
    public StringProperty titleProperty() {
        return new SimpleStringProperty("フォロー");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.setCellFactory(param -> {
            try {
                UserCell userCell = new UserCell();
                userCell.ownerProperty().bind(owner);
                return userCell;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
}
