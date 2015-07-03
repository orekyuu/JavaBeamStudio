package net.orekyuu.javatter.api.twitter.control;

import net.orekyuu.javatter.api.twitter.model.Tweet;

/**
 * リツイートに関する操作を行うことができる
 *
 * @since 1.0.0
 */
public interface RetweetControl {

    /**
     * リツイートを行います。<br>
     * 非同期で行うためには{@link RetweetControl#reTweetAsync(Tweet)}を使用してください。
     *
     * @param tweet リツイートする対象
     * @since 1.0.0
     */
    void reTweet(Tweet tweet);

    /**
     * 非同期でリツイートを行います。
     *
     * @param tweet リツイートする対象
     * @since 1.0.0
     */
    void reTweetAsync(Tweet tweet);
}
