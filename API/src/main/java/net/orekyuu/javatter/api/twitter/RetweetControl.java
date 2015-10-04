package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.util.concurrent.CompletableFuture;

/**
 * リツイートに関する操作
 * @since 1.0.0
 */
public interface RetweetControl {
    /**
     * リツイートします
     * @deprecated {@link #tryRetweet(Tweet)}
     * @since 1.0.0
     * @param tweet リツイートするツイート
     */
    @Deprecated
    void retweet(Tweet tweet);

    /**
     * 非同期でリツイートします
     * @deprecated {@link #tryRetweet(Tweet)}
     * @since 1.0.0
     * @param tweet リツイートするツイート
     */
    @Deprecated
    void retweetAsync(Tweet tweet);

    /**
     * リツイートします
     * @since 1.1.0
     * @param tweet リツイートするツイート
     * @return リツイート結果
     */
    CompletableFuture<Tweet> tryRetweet(Tweet tweet);
}
