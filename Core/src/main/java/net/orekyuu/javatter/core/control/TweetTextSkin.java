package net.orekyuu.javatter.core.control;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TweetTextSkin extends BehaviorSkinBase<TweetText, BehaviorBase<TweetText>> {

    private static final String TWEET_CHANGE_TAG = "TWEET";
    private final TextFlow textFlow;

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

        List<Node> nodes = new ArrayList<>();

        String text = tweet.getText();
        Text label = new Text(text);
        label.setFont(new Font(12));
        nodes.add(label);

        textFlow.getChildren().setAll(nodes);
    }


}
