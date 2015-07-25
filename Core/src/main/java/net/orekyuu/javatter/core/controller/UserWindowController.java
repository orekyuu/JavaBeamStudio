package net.orekyuu.javatter.core.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import net.orekyuu.javatter.api.twitter.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class UserWindowController implements Initializable {
    public ImageView userImage;
    public AnchorPane header;
    public Text userName;
    public TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setUser(User user) {
        String imageURL = user.getProfileBackgroundImageURL();
        header.setStyle(String.format("-fx-background-image: url(\"%s\");-fx-background-repeat: repeat;-fx-background-position: center center;", imageURL));

        userName.setText(user.getName());
        userImage.setImage(new Image(user.getOriginalProfileImageURL()));
    }


}
