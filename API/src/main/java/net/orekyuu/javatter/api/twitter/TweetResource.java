package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.util.concurrent.CompletableFuture;

/**
 * ツイートのリソースにアクセスする操作
 * @since 1.0.0
 */
public interface TweetResource {

    /**
     * ツイートを公式APIから取得します
     * @deprecated {@link #findTweetById(long)}
     * @since 1.0.0
     * @param id StatusID
     * @return 取得したツイート
     */
    @Deprecated
    Tweet findTweet(long id);

    /**
     * ツイートを公式APIから取得します
     * @since 1.1.0
     * @param id StatusID
     * @return 取得したツイート
     */
    CompletableFuture<Tweet> findTweetById(long id);
}
