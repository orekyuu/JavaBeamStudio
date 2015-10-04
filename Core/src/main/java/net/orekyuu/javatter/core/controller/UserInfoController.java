package net.orekyuu.javatter.core.controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import net.orekyuu.javatter.api.twitter.FollowStatus;
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
    public Text followStatus;
    public ToggleButton follow;

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
        follow.textProperty().bind(Bindings.when(follow.selectedProperty()).then("フォローを外す").otherwise("フォローする"));
        owner.fetchFollowStatus(user).thenAccept(result -> {
            Platform.runLater(() -> updateStatus(result));
        });
        follow.setOnAction(e -> {
            if (follow.isSelected()) {
                owner.tryFollow(user);
            } else {
                owner.tryUnfollow(user);
            }
        });
    }

    private void updateStatus(FollowStatus status) {
        switch (status) {
            case INDIFFERENCE: {
                follow.setSelected(false);
                followStatus.setText("無関心");
                break;
            }
            case FOLLOW: {
                follow.setSelected(true);
                followStatus.setText("フォローしています");
                break;
            }
            case FOLLOWER: {
                follow.setSelected(false);
                followStatus.setText("フォローされています");
                break;
            }
            case FRIEND: {
                follow.setSelected(true);
                followStatus.setText("相互フォロー");
                break;
            }
        }
    }

    @Override
    public StringProperty titleProperty() {
        return new SimpleStringProperty("ユーザー情報");
    }
}
