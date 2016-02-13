package net.orekyuu.javatter.api.twitter.model;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.media.Media;

import java.time.LocalDateTime;

/**
 * ツイート
 * @since 1.0.0
 */
public interface Tweet {

    /**
     * @return StatusID
     * @since 1.0.0
     */
    long getStatusId();

    /**
     * @return 発言内容
     * @since 1.0.0
     */
    String getText();

    /**
     * @return 作成された日時
     * @since 1.0.0
     */
    LocalDateTime getCreatedAt();

    /**
     * @return リプライ先のStatusID
     * @since 1.0.0
     */
    long getReplyStatusId();

    /**
     * @return Via
     * @since 1.0.0
     */
    String getViaName();

    /**
     * @return Viaのリンク先
     * @since 1.0.0
     */
    String getViaLink();

    /**
     * @return リツイート元
     * @since 1.0.0
     */
    Tweet getRetweetFrom();

    /**
     * @return 発言したユーザー
     * @since 1.0.0
     */
    User getOwner();

    /**
     * 指定されたユーザーがリツイート済みか
     * @param user ユーザー
     * @return リツイート済みならtrue
     * @since 1.0.0
     */
    boolean isRetweeted(TwitterUser user);

    /**
     * 指定されたユーザーがお気に入り済みか
     * @param user ユーザー
     * @return お気に入り済みならtrue
     * @since 1.0.0
     */
    boolean isFavorited(TwitterUser user);

    /**
     * ツイートに登録されたメディアを返します
     * @return 登録されているメディアのリンク
     * @since 1.0.0
     */
    ImmutableList<String> medias();

    /**
     * ツイートに登録されたメディアを返します。
     * @return 登録されているメディアの情報
     * @since 1.0.2
     */
    default ImmutableList<Media> mediaList() {
        throw new UnsupportedOperationException();
    }
}
