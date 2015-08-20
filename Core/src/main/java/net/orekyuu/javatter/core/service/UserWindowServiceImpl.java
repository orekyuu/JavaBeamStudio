package net.orekyuu.javatter.core.service;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.userwindow.UserWindowService;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.core.controller.UserWindowController;

import java.io.IOException;

public class UserWindowServiceImpl implements UserWindowService {
    @Override
    public void open(User user, TwitterUser owner) {
        JavatterFXMLLoader loader = new JavatterFXMLLoader(getClass()
                .getResource("/layout/userProfile.fxml"));

        Parent root = null;
        try {
            root = loader.load();
            UserWindowController controller = loader.getController();
            controller.setModel(user, owner);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        if (root != null) {
            stage.setScene(new Scene(root));
        }
        stage.setTitle(user.getName());
        stage.show();
    }
}
