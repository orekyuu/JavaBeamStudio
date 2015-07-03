package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.Tweet;

public interface RetweetControl {
    void reTweet(Tweet tweet);

    void reTweetAsync(Tweet tweet);
}
