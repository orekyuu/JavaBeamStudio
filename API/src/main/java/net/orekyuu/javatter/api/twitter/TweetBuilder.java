package net.orekyuu.javatter.api.twitter;


import java.io.File;

/**
 * ツイートを作成するためのビルダーです。
 *
 * @since 1.0.0
 */
public interface TweetBuilder {

    /**
     * 発言する内容を設定します。
     * @param text 発現する文字列
     * @return メソッドチェーンを行うためのthis
     * @since 1.0.0
     */
    TweetBuilder setText(String text);

    /**
     * ツイートにメディアを添付します。
     * @param file 添付するファイル
     * @return メソッドチェーンを行うためのthis
     * @since 1.0.0
     */
    TweetBuilder addFile(File file);

    /**
     * ツイートを非同期で行うTweetBuilderを返します。<br>
     * 設定した内容は引き継がれます。
     * @return ツイートを非同期で行うビルダー
     * @since 1.0.0
     */
    AsyncTweetBuilder setAsync();

    /**
     * ツイートを行います。<br>
     * @since 1.0.0
     */
    void tweet();
}
