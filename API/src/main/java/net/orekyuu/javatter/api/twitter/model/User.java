package net.orekyuu.javatter.api.twitter.model;

import java.time.LocalDateTime;

/**
 * ユーザー
 */
public interface User {
    /**
     * @return ユーザーが作成された日時
     * @since 1.0.0
     */
    LocalDateTime getCreatedAt();

    /**
     * @return ユーザーの説明文
     * @since 1.0.0
     */
    String getDescription();

    /**
     * @return お気に入り数
     * @since 1.0.0
     */
    int getFavCount();

    /**
     * @return フォロワー数
     * @since 1.0.0
     */
    int getFollowersCount();

    /**
     * @return フォロー数
     * @since 1.0.0
     */
    int getFriendsCount();

    /**
     * @return ID
     * @since 1.0.0
     */
    long getId();

    /**
     * @return リスト数
     * @since 1.0.0
     */
    int getListedCount();

    /**
     * @return 場所
     * @since 1.0.0
     */
    String getLocation();

    /**
     * @return ユーザー名
     * @since 1.0.0
     */
    String getName();

    /**
     * @return スクリーンネーム
     * @since 1.0.0
     */
    String getScreenName();

    /**
     * @return ユーザーアイコン
     * @since 1.0.0
     */
    String getProfileImageURL();

    /**
     * @return オリジナルサイズのアイコン
     * @since 1.0.0
     */
    String getOriginalProfileImageURL();

    /**
     * @return プロフィールの背景画像
     * @since 1.0.0
     */
    String getProfileBackgroundImageURL();

    /**
     * @return ツイート数
     * @since 1.0.0
     */
    int getTweetCount();

    /**
     * @return ウェブサイト
     * @since 1.0.0
     */
    String getWebSite();
}
