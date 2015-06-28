package net.orekyuu.javatter.api.twitter;

/**
 * ツイートの成功した時のコールバック
 *
 * @since 1.0.0
 */
@FunctionalInterface
public interface TweetSuccessCallback {

    /**
     * ツイートに成功した時に呼び出されます。
     * @since 1.0.0
     */
    void success();
}
