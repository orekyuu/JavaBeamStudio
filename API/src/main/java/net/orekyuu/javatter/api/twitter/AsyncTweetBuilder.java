package net.orekyuu.javatter.api.twitter;

import java.util.concurrent.ExecutorService;


public interface AsyncTweetBuilder extends TweetBuilder {

    AsyncTweetBuilder setSuccessCallback(TweetSuccessCallback callback);

    AsyncTweetBuilder setFailedCallback(TweetFailedCallback callback);

    AsyncTweetBuilder setExecutor(ExecutorService executorService);
}
