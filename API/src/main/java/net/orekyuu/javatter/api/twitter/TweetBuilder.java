package net.orekyuu.javatter.api.twitter;


import java.io.File;

public interface TweetBuilder {

    TweetBuilder setText(String text);

    TweetBuilder addFile(File file);

    AsyncTweetBuilder setAsync();

    void tweet();
}
