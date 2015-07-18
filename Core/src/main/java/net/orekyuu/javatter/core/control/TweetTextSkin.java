package net.orekyuu.javatter.core.control;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.ListAdapter;
import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
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
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class TweetTextSkin extends BehaviorSkinBase<TweetText, BehaviorBase<TweetText>> {

    private static final String TWEET_CHANGE_TAG = "TWEET";
    private final TextFlow textFlow;
    private static final Logger logger = Logger.getLogger(TweetTextSkin.class.getName());

    /**
     * Constructor for all BehaviorSkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    protected TweetTextSkin(TweetText control) {
        super(control, new BehaviorBase<>(control, Collections.<KeyBinding>emptyList()));

        textFlow = new TextFlow();
        textFlow.setPrefWidth(Region.USE_COMPUTED_SIZE);
        getChildren().add(textFlow);

        updateText();
        registerChangeListener(control.tweetProperty(), TWEET_CHANGE_TAG);
    }

    @Override
    protected void handleControlPropertyChanged(String p) {
        super.handleControlPropertyChanged(p);

        if (p.equals(TWEET_CHANGE_TAG)) { //$NON-NLS-1$
            updateText();
        }
    }

    private void updateText() {
        Tweet tweet = getSkinnable().getTweet();
        if (tweet == null) {
            textFlow.getChildren().clear();
            return;
        }

        if (!(tweet instanceof TweetImpl)) {
            String text = tweet.getText();
            Text label = new Text(text);
            label.setFont(new Font(12));
            textFlow.getChildren().setAll(label);

            logger.warning("Tweet instance is not TweetImpl class.");
            return;
        }
        TweetImpl tweetImpl = (TweetImpl) tweet;

        List<TweetEntity> tweetEntities = new ArrayList<>();
        tweetEntities.addAll(new ArrayList<>(Arrays.asList(tweetImpl.getHashtags())));
        tweetEntities.addAll(new ArrayList<>(Arrays.asList(tweetImpl.getUrls())));
        tweetEntities.addAll(new ArrayList<>(Arrays.asList(tweetImpl.getMentions())));
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
                    Consumer<String> onClickHashTag = getSkinnable().getOnClickHashTag();
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
                    Consumer<String> onClickUserLink = getSkinnable().getOnClickUserLink();
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
                    Consumer<String> onClickLink = getSkinnable().getOnClickLink();
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
        textFlow.getChildren().setAll(nodes);
    }

    private void addTextEntity(int last, int start, List<Node> nodes, String text) {
        String t = text.substring(last, start);
        if (t.isEmpty()) {
            return;
        }
        Text label = new Text(t);
        label.setFont(new Font(12));
        nodes.add(label);
    }

}
