package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.Tweet;

/**
 * ツイートのリソースにアクセスする操作
 */
public interface TweetResource {

    /**
     * ツイートを公式APIから取得します
     * @since 1.0.0
     * @param id StatusID
     * @return 取得したツイート
     */
    Tweet findTweet(long id);
}
