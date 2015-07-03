package net.orekyuu.javatter.core.twitter;

import com.gs.collections.impl.list.mutable.FastList;
import net.orekyuu.javatter.api.twitter.AsyncTweetBuilder;
import net.orekyuu.javatter.api.twitter.TweetFailedCallback;
import net.orekyuu.javatter.api.twitter.TweetSuccessCallback;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import twitter4j.Twitter;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTweetBuilderImpl extends TweetBuilderImpl implements AsyncTweetBuilder {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setName("AsyncTweetThread");
        thread.setDaemon(true);
        return thread;
    });
    private ExecutorService useExecutor;
    private TweetSuccessCallback successCallback;
    private TweetFailedCallback failedCallback;

    protected AsyncTweetBuilderImpl(String text, Twitter twitter, FastList<File> files, Optional<Tweet> reply) {
        super(twitter);
        setText(text);
        for (File file : files) {
            addFile(file);
        }
        reply.ifPresent(this::replyTo);
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

    @Override
    public void tweet() {
        ExecutorService executor = useExecutor == null ? executorService : useExecutor;
        executor.execute(() -> {
            try {
                super.tweet();
                if (successCallback != null) {
                    successCallback.success();
                }
            } catch (Exception e) {
                if (failedCallback != null) {
                    failedCallback.failed();
                }
            }
        });
    }
}
