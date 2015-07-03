package net.orekyuu.javatter.api.twitter;

/**
 * ツイートに失敗した時のコールバック
 *
 * @since 1.0.0
 */
@FunctionalInterface
public interface TweetFailedCallback {

    /**
     * ツイートに失敗した時のコールバック
     *
     * @since 1.0.0
     */
    void failed();
}
