package net.orekyuu.javatter.core.control;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.ListAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.core.twitter.model.TweetImpl;
import twitter4j.HashtagEntity;
import twitter4j.TweetEntity;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public final class TweetText extends TextFlow {

    private ObjectProperty<Tweet> tweet;
    private ObjectProperty<Consumer<String>> onClickLink;
    private ObjectProperty<Consumer<String>> onClickUserLink;
    private ObjectProperty<Consumer<String>> onClickHashTag;

    private static final Logger logger = Logger.getLogger(TweetText.class.getName());

    public TweetText(Tweet tweet) {
        super();
        setTweet(tweet);
        updateText();
        setPrefWidth(Region.USE_COMPUTED_SIZE);
    }

    public TweetText() {
        super();
        setPrefWidth(Region.USE_COMPUTED_SIZE);
    }


    private void updateText() {
        Tweet tweet = getTweet();
        if (tweet == null) {
            getChildren().clear();
            return;
        }

        if (!(tweet instanceof TweetImpl)) {
            String text = tweet.getText();
            Text label = new Text(text);
            label.setFont(new Font(12));
            getChildren().setAll(label);

            logger.warning("Tweet instance is not TweetImpl class.");
            return;
        }
        TweetImpl tweetImpl = (TweetImpl) tweet;

        List<TweetEntity> tweetEntities = new ArrayList<>();
        tweetEntities.addAll(new ArrayList<>(Arrays.asList(tweetImpl.getHashtags())));
        tweetEntities.addAll(new ArrayList<>(Arrays.asList(tweetImpl.getUrls())));
        tweetEntities.addAll(new ArrayList<>(Arrays.asList(tweetImpl.getMentions())));
        tweetEntities.addAll(new ArrayList<>(Arrays.asList(tweetImpl.getMedias())));
        MutableList<TweetEntity> entities = ListAdapter.adapt(tweetEntities).sortThisByInt(TweetEntity::getStart);

        List<Node> nodes = new ArrayList<>();
        String text = tweet.getText();
        int lastIndex = 0;
        for (TweetEntity entity : entities) {
            Node entityNode = null;
            if (entity instanceof HashtagEntity) {
                HashtagEntity hashtagEntity = (HashtagEntity) entity;
                Hyperlink hyperlink = new Hyperlink("#" + hashtagEntity.getText());
                hyperlink.setFont(new Font(12));
                hyperlink.setOnAction(e -> {
                    Consumer<String> onClickHashTag = getOnClickHashTag();
                    if (onClickHashTag != null) {
                        onClickHashTag.accept(hyperlink.getText());
                    }
                });
                entityNode = hyperlink;
            } else if (entity instanceof UserMentionEntity) {
                UserMentionEntity mentionEntity = (UserMentionEntity) entity;
                Hyperlink hyperlink = new Hyperlink("@" + mentionEntity.getText());
                hyperlink.setFont(new Font(12));
                hyperlink.setOnAction(e -> {
                    Consumer<String> onClickUserLink = getOnClickUserLink();
                    if (onClickUserLink != null) {
                        onClickUserLink.accept(mentionEntity.getScreenName());
                    }
                });
                entityNode = hyperlink;
            } else if (entity instanceof URLEntity) {
                URLEntity urlEntity = (URLEntity) entity;
                Hyperlink hyperlink = new Hyperlink(urlEntity.getExpandedURL());
                hyperlink.setFont(new Font(12));
                hyperlink.setOnAction(e -> {
                    Consumer<String> onClickLink = getOnClickLink();
                    if (onClickLink != null) {
                        onClickLink.accept(urlEntity.getExpandedURL());
                    }
                });
                entityNode = hyperlink;
            }
            addTextEntity(lastIndex, entity.getStart(), nodes, text);
            lastIndex = entity.getEnd();
            nodes.add(entityNode);
        }
        addTextEntity(lastIndex, text.length(), nodes, text);
        getChildren().setAll(nodes);
    }

    private void addTextEntity(int last, int start, List<Node> nodes, String text) {
        if (last >= text.length()) {
            return;
        }
        String t = text.substring(last, start);
        if (t.isEmpty()) {
            return;
        }
        Text label = new Text(t);
        label.setFont(new Font(12));
        label.setStyle("-fx-text-fill: black;");
        nodes.add(label);
    }

    //Properties
    //TweetProperty
    public Tweet getTweet() {
        return tweet == null ? null : tweet.get();
    }

    public ObjectProperty<Tweet> tweetProperty() {
        if (tweet == null) {
            tweet = new SimpleObjectProperty<>(this, "tweet");
            tweet.addListener((observable, oldValue, newValue) -> {
                updateText();
            });
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
