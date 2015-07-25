package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.Tweet;

public interface RetweetControl {
    void retweet(Tweet tweet);

    void retweetAsync(Tweet tweet);
}
