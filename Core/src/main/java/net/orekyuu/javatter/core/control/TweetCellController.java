package net.orekyuu.javatter.core.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import net.orekyuu.javatter.api.service.UserIconStorage;
import net.orekyuu.javatter.api.twitter.model.Tweet;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class TweetCellController implements Initializable {

    public Label tweetContent;
    public ImageView currentIcon;
    public Hyperlink via;
    public Label userName;
    private ObjectProperty<Tweet> tweet = new SimpleObjectProperty<>();
    @Inject
    private UserIconStorage iconStorage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tweet.addListener(this::onChange);
    }

    private void onChange(ObservableValue<? extends Tweet> observable, Tweet oldValue, Tweet newValue) {
        tweetContent.setText(newValue.getText());
        currentIcon.setImage(iconStorage.find(newValue.getOwner()));
        via.setText(newValue.getViaName());
        userName.setText(String.format("@%s / %s", newValue.getOwner().getScreenName(), newValue.getOwner().getName()));
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
}
