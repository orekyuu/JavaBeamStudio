package net.orekyuu.javatter.core.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.util.function.Consumer;

public final class TweetText extends Control {

    private ObjectProperty<Tweet> tweet;
    private ObjectProperty<Consumer<String>> onClickLink;
    private ObjectProperty<Consumer<String>> onClickUserLink;
    private ObjectProperty<Consumer<String>> onClickHashTag;

    public TweetText(Tweet tweet) {
        super();
        setTweet(tweet);
    }

    public TweetText() {
        super();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TweetTextSkin(this);
    }

    //TweetProperty
    public Tweet getTweet() {
        return tweet == null ? null : tweet.get();
    }

    public ObjectProperty<Tweet> tweetProperty() {
        if (tweet == null) {
            tweet = new SimpleObjectProperty<>(this, "tweet");
        }
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        tweetProperty().set(tweet);
    }

    //onClickLink
    public Consumer<String> getOnClickLink() {
        return onClickLink == null ? null : onClickLink.get();
    }

    public ObjectProperty<Consumer<String>> onClickLinkProperty() {
        if (onClickLink == null) {
            onClickLink = new SimpleObjectProperty<>();
        }
        return onClickLink;
    }

    public void setOnClickLink(Consumer<String> onClickLink) {
        this.onClickLinkProperty().set(onClickLink);
    }

    //onClickUserLink
    public Consumer<String> getOnClickUserLink() {
        return onClickUserLink == null ? null : onClickUserLink.get();
    }

    public ObjectProperty<Consumer<String>> onClickUserLinkProperty() {
        if (onClickUserLink == null) {
            onClickUserLink = new SimpleObjectProperty<>();
        }
        return onClickUserLink;
    }

    public void setOnClickUserLink(Consumer<String> onClickUserLink) {
        this.onClickUserLinkProperty().set(onClickUserLink);
    }

    //onClickHashTag
    public Consumer<String> getOnClickHashTag() {
        return onClickHashTag == null ? null : onClickHashTag.get();
    }

    public ObjectProperty<Consumer<String>> onClickHashTagProperty() {
        if (onClickHashTag == null) {
            onClickHashTag = new SimpleObjectProperty<>();
        }
        return onClickHashTag;
    }

    public void setOnClickHashTag(Consumer<String> onClickHashTag) {
        this.onClickHashTagProperty().set(onClickHashTag);
    }
}
