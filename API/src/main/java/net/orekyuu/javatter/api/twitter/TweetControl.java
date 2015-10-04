package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.util.concurrent.CompletableFuture;

/**
 * @since 1.0.0
 */
public interface TweetControl {

    /**
     * ツイートを削除します
     * @deprecated {@link #deleteTweet(Tweet)}
     * @param tweet 削除するツイート
     * @since 1.0.0
     */
    @Deprecated
    void removeTweet(Tweet tweet);

    /**
     * 非同期でツイートを削除します
     * @deprecated {@link #deleteTweet(Tweet)}
     * @param tweet 削除するツイート
     * @since 1.0.0
     */
    @Deprecated
    void removeTweetAsync(Tweet tweet);

    /**
     * ツイートを削除します。
     * @since 1.1.0
     * @param tweet 削除するツイート
     * @return 削除結果
     */
    CompletableFuture<Tweet> deleteTweet(Tweet tweet);

    /**
     * ツイートを作成します。
     *
     * @return ツイートを作成するためのビルダー
     * @since 1.0.0
     */
    TweetBuilder createTweet();
}
