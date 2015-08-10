package net.orekyuu.javatter.core.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.orekyuu.javatter.api.service.CurrentTweetAreaService;
import net.orekyuu.javatter.api.service.UserIconStorage;
import net.orekyuu.javatter.api.service.UserWindowService;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.core.settings.storage.GeneralSetting;
import net.orekyuu.javatter.core.settings.storage.SettingsStorage;

import javax.inject.Inject;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TweetCellController implements Initializable {

    public TweetText tweetContent;
    public ImageView currentIcon;
    public Hyperlink via;
    public Label userName;
    public Button reply;
    public Button retweet;
    public ToggleButton favorite;
    public GridPane root;
    public HBox images;
    private ObjectProperty<Tweet> tweet = new SimpleObjectProperty<>();
    private ObjectProperty<TwitterUser> owner = new SimpleObjectProperty<>();
    @Inject
    private UserIconStorage iconStorage;
    @Inject
    private CurrentTweetAreaService tweetAreaService;
    @Inject
    private UserWindowService service;
    @Inject
    private SettingsStorage settingsStorage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tweet.addListener(this::onChange);
        tweetContent.setOnClickLink(url -> {
            try {
                Desktop.getDesktop().browse(new URL(url).toURI());
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        tweetContent.setOnClickUserLink(userName -> {
            TwitterUser user = owner.get();
            if (user != null) {
                service.open(user.findUser(userName));
            }
        });
    }

    private void onChange(ObservableValue<? extends Tweet> observable, Tweet oldValue, Tweet newValue) {
        tweetContent.setTweet(newValue);
        currentIcon.setImage(iconStorage.find(newValue.getOwner()));
        via.setText(newValue.getViaName());
        userName.setText(String.format("@%s / %s", newValue.getOwner().getScreenName(), newValue.getOwner().getName()));
        TwitterUser twitterUser = owner.get();
        if (twitterUser != null) {
            updateButtonState(newValue, twitterUser);
        }

        images.getChildren().clear();
        for (String url : newValue.medias()) {
            Image image = new Image(url, 128, 128, true, true, true);
            ImageView view = new ImageView(image);
            view.setOnMouseClicked(e -> openPreview(url));
            images.getChildren().add(view);
        }

        currentIcon.setOnMouseClicked(e -> {
            service.open(newValue.getOwner());
        });
    }

    private void updateButtonState(Tweet tweet, TwitterUser twitterUser) {
        User owner = tweet.getOwner();
        retweet.setDisable(owner.getId() == twitterUser.getUser().getId() || tweet.isRetweeted(twitterUser));
        favorite.setSelected(tweet.isFavorited(twitterUser));

    }

    private void openPreview(String url) {
        Stage stage = new Stage();
        stage.centerOnScreen();
        Image image = new Image(url, true);
        BorderPane pane = new BorderPane();
        ProgressIndicator indicator = new ProgressIndicator(0);
        indicator.setMaxSize(150, 150);
        pane.setCenter(indicator);
        Scene progressScene = new Scene(pane, 400, 400);
        image.progressProperty().addListener((observable, oldValue, newValue) -> {
            if (1.0 <= newValue.doubleValue()) {
                ImageView imageView = new ImageView(image);
                BorderPane borderPane = new BorderPane();
                borderPane.setCenter(imageView);
                Scene scene = new Scene(borderPane);
                borderPane.setPrefSize(image.getWidth(), image.getHeight());
                stage.setScene(scene);
                imageView.fitWidthProperty().bind(borderPane.widthProperty());
                imageView.fitHeightProperty().bind(borderPane.heightProperty());
            } else {
                indicator.setProgress(newValue.doubleValue());
            }
        });
        stage.setScene(progressScene);
        stage.setTitle(url);
        stage.show();
    }


    public void setTweet(Tweet tweet) {
        this.tweet.set(tweet);
    }

    public Tweet getTweet() {
        return tweet.get();
    }

    public ObjectProperty<Tweet> tweetProperty() {
        return tweet;
    }

    public TwitterUser getOwner() {
        return owner.get();
    }

    public ObjectProperty<TwitterUser> ownerProperty() {
        return owner;
    }

    public void setOwner(TwitterUser owner) {
        this.owner.set(owner);
    }

    public void clickReply() {
        Tweet tweet = getTweet();
        if (tweet != null) {
            tweetAreaService.insertTop("@" + tweet.getOwner().getScreenName() + " ");
            tweetAreaService.setReply(tweet);
        }
    }

    public void clickRetweet() {
        GeneralSetting setting = settingsStorage.getGeneralSetting();
        if (setting.isCheckRT()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "リツイートしますか？", ButtonType.YES, ButtonType.NO);
            alert.setTitle("確認");
            Optional<ButtonType> result = alert.showAndWait();
            result.filter(t -> t == ButtonType.YES).ifPresent(t -> retweet());
        } else {
            retweet();
        }
    }

    private void retweet() {
        TwitterUser twitterUser = owner.get();
        Tweet tweet = this.tweet.get();
        if (twitterUser != null && tweet != null) {
            twitterUser.retweetAsync(tweet);
        }
    }

    public void clickFavorite() {
        GeneralSetting setting = settingsStorage.getGeneralSetting();
        if (setting.isCheckFavorite()) {
            String str = favorite.isSelected() ? "ふぁぼ" : "あんふぁぼ";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, str + "しますか？", ButtonType.YES, ButtonType.NO);
            alert.setTitle("確認");
            Optional<ButtonType> result = alert.showAndWait();
            result.ifPresent(t -> {
                if (t == ButtonType.YES) {
                    favorite();
                } else {
                    favorite.setSelected(!favorite.isSelected());
                }
            });
        } else {
            favorite();
        }
    }

    private void favorite() {
        TwitterUser twitterUser = owner.get();
        Tweet tweet = this.tweet.get();
        if (twitterUser != null && tweet != null) {
            if (favorite.isSelected()) {
                twitterUser.favoriteAsync(tweet);
            } else {
                twitterUser.unFavoriteAsync(tweet);
            }
        }
    }
}
