package net.orekyuu.javatter.api.twitter;

import com.gs.collections.api.list.MutableList;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;

import java.util.concurrent.CompletableFuture;

/**
 * Userのリソースにアクセスする操作
 * @since 1.0.0
 */
public interface UserResource {

    /**
     * 公式APIからスクリーンネームを使用してユーザー情報を取得します
     * @since 1.0.0
     * @deprecated {@link UserResource#findUserByScreenName(String)}を使用してください。
     * @param screenName スクリーンネーム
     * @return 取得したユーザー
     */
    @Deprecated
    User findUser(String screenName);

    /**
     * 公式APIからスクリーンネームを使用してユーザー情報を取得します
     * @since 1.1.0
     * @param screenName スクリーンネーム
     * @return 検索結果
     */
    CompletableFuture<User> findUserByScreenName(String screenName);

    /**
     * 公式APIからIDを使用してユーザー情報を取得します
     * @deprecated {@link UserResource#findUserById(long)}
     * @since 1.0.0
     * @param id id
     * @return 取得したユーザー
     */
    @Deprecated
    User findUser(long id);

    /**
     * 公式APIからIDを使用してユーザー情報を取得します
     * @since 1.1.0
     * @param id id
     * @return 取得したユーザー
     */
    CompletableFuture<User> findUserById(long id);

    /**
     * ユーザーのタイムラインを公式APIから取得します
     * @deprecated {@link #fetchTimeline(User)}
     * @since 1.0.0
     * @param user ユーザー
     * @return 取得したTL
     */
    @Deprecated
    MutableList<Tweet> getUserTimeline(User user);

    /***
     * ユーザーのタイムラインを公式APIから取得します
     * @since 1.1.0
     * @param user ユーザー
     * @return 取得したTL
     */
    CompletableFuture<MutableList<Tweet>> fetchTimeline(User user);

    /**
     * ユーザーのタイムラインを公式APIから取得します
     * @deprecated {@link #fetchTimeline(long)}
     * @since 1.0.0
     * @param id ユーザーID
     * @return 取得したTL
     */
    @Deprecated
    MutableList<Tweet> getUserTimeline(long id);

    /**
     * ユーザーのタイムラインを公式APIから取得します
     * @since 1.1.0
     * @param id ユーザーID
     * @return 取得したTL
     */
    CompletableFuture<MutableList<Tweet>> fetchTimeline(long id);

    /**
     * ユーザーのお気に入りを公式APIから取得します
     * @deprecated {@link #fetchFavorites(User)}
     * @since 1.0.0
     * @param user ユーザー
     * @return 取得したお気に入り
     */
    @Deprecated
    MutableList<Tweet> getUserFavorites(User user);

    /**
     * ユーザーのお気に入りを公式APIから取得します
     * @since 1.1.0
     * @param user ユーザー
     * @return 取得したお気に入り
     */
    CompletableFuture<MutableList<Tweet>> fetchFavorites(User user);

    /**
     * ユーザーのお気に入りを公式APIから取得します
     * @deprecated {@link #fetchFavorites(long)}
     * @since 1.0.0
     * @param id ユーザーID
     * @return 取得したお気に入り
     */
    @Deprecated
    MutableList<Tweet> getUserFavorites(long id);

    /**
     * ユーザーのお気に入りを公式APIから取得します
     * @since 1.1.0
     * @param id ユーザーID
     * @return 取得したお気に入り
     */
    CompletableFuture<MutableList<Tweet>> fetchFavorites(long id);

    /**
     * フォロワーを取得します
     * @deprecated {@link #fetchFollowers(User)}
     * @since 1.0.0
     * @param user ユーザー
     * @return 取得したフォロワー
     */
    @Deprecated
    MutableList<User> getFollowers(User user);

    /**
     * フォロワーを取得します
     * @since 1.1.0
     * @param user ユーザー
     * @return 取得したフォロワー
     */
    CompletableFuture<MutableList<User>> fetchFollowers(User user);

    /**
     * フォロワーを取得します
     * @deprecated {@link #fetchFollowers(long)}
     * @since 1.0.0
     * @param id ユーザーID
     * @return 取得したフォロワー
     */
    @Deprecated
    MutableList<User> getFollowers(long id);

    /**
     * フォロワーを取得します
     * @since 1.0.0
     * @param id ユーザーID
     * @return 取得したフォロワー
     */
    CompletableFuture<MutableList<User>> fetchFollowers(long id);

    /**
     * フォローしているユーザーを取得します
     * @deprecated {@link #fetchFriends(User)}
     * @since 1.0.0
     * @param user ユーザー
     * @return 取得したフォローしているユーザー
     */
    @Deprecated
    MutableList<User> getFriends(User user);

    /**
     * フォローしているユーザーを取得します
     * @since 1.1.0
     * @param user ユーザー
     * @return 取得したフォローしているユーザー
     */
    CompletableFuture<MutableList<User>> fetchFriends(User user);

    /**
     * フォローしているユーザーを取得します
     * @since 1.0.0
     * @param id ユーザーID
     * @return 取得したフォローしているユーザー
     */
    @Deprecated
    MutableList<User> getFriends(long id);

    /**
     * フォローしているユーザーを取得します
     * @since 1.1.0
     * @param id ユーザーID
     * @return 取得したフォローしているユーザー
     */
    CompletableFuture<MutableList<User>> fetchFriends(long id);
}
