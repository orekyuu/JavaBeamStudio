package net.orekyuu.javatter.core.controller;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.twitter.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserWindowController implements Initializable {
    public ImageView userImage;
    public AnchorPane header;
    public TabPane tabPane;

    private UserInfoController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            JavatterFXMLLoader loader = new JavatterFXMLLoader(getClass().getResource("/layout/userInfo.fxml"));
            Parent p = loader.load();
            tabPane.getTabs().add(new Tab("ユーザー情報", p));
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setUser(User user) {
        String imageURL = user.getProfileBackgroundImageURL();
        header.setStyle(String.format("-fx-background-image: url(\"%s\");-fx-background-repeat: repeat;-fx-background-position: center center;", imageURL));

        userImage.setImage(new Image(user.getOriginalProfileImageURL()));

        if (controller != null) {
            controller.setUser(user);
        }
    }


}
