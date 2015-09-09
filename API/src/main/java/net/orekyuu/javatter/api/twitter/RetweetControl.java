package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.Tweet;

/**
 * リツイートに関する操作
 * @since 1.0.0
 */
public interface RetweetControl {
    /**
     * リツイートします
     * @since 1.0.0
     * @param tweet リツイートするツイート
     */
    void retweet(Tweet tweet);

    /**
     * 非同期でリツイートします
     * @since 1.0.0
     * @param tweet リツイートするツイート
     */
    void retweetAsync(Tweet tweet);
}
