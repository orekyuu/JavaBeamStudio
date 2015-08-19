package net.orekyuu.javatter.core.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.userwindow.UserWindowTab;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class UserInfoController implements UserWindowTab {
    public Text tweetCount;
    public Text followCount;
    public Text followerCount;
    public Text favoriteCount;
    public ImageView userIcon;
    public Text name;
    public Hyperlink screenName;
    public Label desc;
    public Hyperlink webSite;
    public Text created;

    @Override
    public void update(User user, TwitterUser owner) {
        tweetCount.setText(String.valueOf(user.getTweetCount()));
        followCount.setText(String.valueOf(user.getFriendsCount()));
        followerCount.setText(String.valueOf(user.getFollowersCount()));
        favoriteCount.setText(String.valueOf(user.getFavCount()));
        userIcon.setImage(new Image(user.getOriginalProfileImageURL(), true));
        name.setText(user.getName());
        screenName.setText(user.getScreenName());
        screenName.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URL("http://twitter.com/" + user.getScreenName() + "/").toURI());
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        });
        desc.setText(user.getDescription());

        if (user.getWebSite() != null) {
            webSite.setVisible(true);
            webSite.setDisable(false);
            webSite.setText(user.getWebSite());
            webSite.setOnAction(e -> {
                try {
                    Desktop.getDesktop().browse(new URL(user.getWebSite()).toURI());
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });
        } else {
            webSite.setVisible(false);
            webSite.setDisable(true);
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/ddに作成されました。");
        created.setText(user.getCreatedAt().format(format));

    }

    @Override
    public StringProperty titleProperty() {
        return new SimpleStringProperty("ユーザー情報");
    }
}
