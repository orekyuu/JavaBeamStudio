package net.orekyuu.javatter.core.twitter;

import com.gs.collections.impl.list.mutable.FastList;
import net.orekyuu.javatter.api.twitter.AsyncTweetBuilder;
import net.orekyuu.javatter.api.twitter.TweetBuilder;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public class TweetBuilderImpl implements TweetBuilder {

    private String text;
    private FastList<File> files = FastList.newList();
    private Twitter twitter;
    private static final Logger logger = Logger.getLogger(TweetBuilderImpl.class.getName());

    public TweetBuilderImpl(Twitter twitter) {
        this.twitter = twitter;
    }

    @Override
    public TweetBuilder setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public TweetBuilder addFile(File file) {
        files.add(file);
        return this;
    }

    @Override
    public AsyncTweetBuilder setAsync() {
        return new AsyncTweetBuilderImpl(text, twitter, files);
    }

    @Override
    public void tweet() {
        //アカウントとテキストはnullにできない
        Objects.requireNonNull(twitter, "User is null.");
        Objects.requireNonNull(text, "TweetText is null.");
        System.out.println(getClass().getName() + " ツイート:" + text);

        StatusUpdate status = new StatusUpdate(text);
        try {
            twitter.updateStatus(status);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
