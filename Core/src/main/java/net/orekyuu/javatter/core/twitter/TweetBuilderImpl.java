package net.orekyuu.javatter.core.twitter;

import com.gs.collections.impl.list.mutable.FastList;
import net.orekyuu.javatter.api.twitter.AsyncTweetBuilder;
import net.orekyuu.javatter.api.twitter.TweetBuilder;
import net.orekyuu.javatter.api.twitter.TweetFailedCallback;
import net.orekyuu.javatter.api.twitter.TweetSuccessCallback;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TweetBuilderImpl implements AsyncTweetBuilder {

    private String text;
    private FastList<File> files = FastList.newList();
    private Twitter twitter;
    private Optional<Tweet> reply = Optional.empty();
    private static final Logger logger = Logger.getLogger(TweetBuilderImpl.class.getName());

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setName("AsyncTweetThread");
        thread.setDaemon(true);
        return thread;
    });
    private ExecutorService useExecutor;
    private TweetSuccessCallback successCallback;
    private TweetFailedCallback failedCallback;
    private boolean isAsync = false;

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
    public TweetBuilder replyTo(Tweet tweet) {
        reply = Optional.ofNullable(tweet);
        return this;
    }

    @Override
    public AsyncTweetBuilder setAsync() {
        isAsync = true;
        return this;
    }

    private void doTweet() throws TwitterException {
        //アカウントとテキストはnullにできない
        Objects.requireNonNull(twitter, "User is null.");
        Objects.requireNonNull(text, "TweetText is null.");

        StatusUpdate status = new StatusUpdate(text);
        status.media(files.getFirst());
        reply.map(Tweet::getStatusId).ifPresent(status::setInReplyToStatusId);
        twitter.updateStatus(status);
    }

    @Override
    public void tweet() {

        if (isAsync) {
            ExecutorService executor = useExecutor == null ? executorService : useExecutor;
            executor.execute(() -> {
                try {
                    doTweet();
                    if (successCallback != null) {
                        successCallback.success();
                    }
                } catch (Exception e) {
                    if (failedCallback != null) {
                        failedCallback.failed();
                    }
                }
            });
        } else {
            try {
                doTweet();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public AsyncTweetBuilder setSuccessCallback(TweetSuccessCallback callback) {
        this.successCallback = callback;
        return this;
    }

    @Override
    public AsyncTweetBuilder setFailedCallback(TweetFailedCallback callback) {
        this.failedCallback = callback;
        return this;
    }

    @Override
    public AsyncTweetBuilder setExecutor(ExecutorService executorService) {
        useExecutor = executorService;
        return this;
    }
}
