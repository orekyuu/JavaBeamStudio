package net.orekyuu.javatter.api.action;

import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.Tweet;

/**
 * アクション実行時の引数となる値クラスです。
 */
public final class TweetCellActionEvent {
    private final Tweet tweet;
    private final TwitterUser owner;

    /**
     * アクションのイベントを作成します。
     * @since 1.0.0
     * @param tweet 対象となるツイート
     * @param owner ツイートを監視しているユーザー
     */
    public TweetCellActionEvent(Tweet tweet, TwitterUser owner) {
        this.tweet = tweet;
        this.owner = owner;
    }

    /**
     * @since 1.0.0
     * @return 対象となるツイート
     */
    public Tweet getTweet() {
        return tweet;
    }

    /**
     * @since 1.0.0
     * @return ツイートを監視しているユーザー
     */
    public TwitterUser getOwner() {
        return owner;
    }
}
