package net.orekyuu.javatter.api.twitter;

import java.util.concurrent.ExecutorService;


/**
 * 非同期でツイートを行うTweetBuilderです。<br>
 * ツイートの処理を行うときは別のスレッドで処理されるため、処理をブロックすることはありません。<br>
 * ツイートに使用されるスレッドはデフォルトでプーリングされています。
 *
 * @since 1.0.0
 */
public interface AsyncTweetBuilder extends TweetBuilder {

    /**
     * ツイートが成功した時のコールバックを設定します。<br>
     * このコールバックはバックグラウンドスレッドで実行されます。
     *
     * @param callback ツイートが成功した時のコールバック
     * @return メソッドチェーンのためのthis
     * @since 1.0.0
     */
    AsyncTweetBuilder setSuccessCallback(TweetSuccessCallback callback);

    /**
     * ツイートが失敗した時のコールバックを設定します。<br>
     * このコールバックはバックグラウンドスレッドで実行されます。
     *
     * @param callback ツイートが失敗した時のコールバック
     * @return メソッドチェーンのためのthis
     * @since 1.0.0
     */
    AsyncTweetBuilder setFailedCallback(TweetFailedCallback callback);

    /**
     * 非同期TweetBuilderで使用されるExecutorServiceを設定します。
     *
     * @param executorService 非同期TweetBuilderで使用される{@link ExecutorService}
     * @return メソッドチェーンのためのthis
     * @since 1.0.0
     */
    AsyncTweetBuilder setExecutor(ExecutorService executorService);
}
