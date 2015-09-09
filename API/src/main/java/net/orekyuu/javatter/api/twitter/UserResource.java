package net.orekyuu.javatter.api.twitter;

import com.gs.collections.api.list.MutableList;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;

/**
 * Userのリソースにアクセスする操作
 * @since 1.0.0
 */
public interface UserResource {

    /**
     * 公式APIからスクリーンネームを使用してユーザー情報を取得します
     * @since 1.0.0
     * @param screenName スクリーンネーム
     * @return 取得したユーザー
     */
    User findUser(String screenName);

    /**
     * 公式APIからIDを使用してユーザー情報を取得します
     * @since 1.0.0
     * @param id id
     * @return 取得したユーザー
     */
    User findUser(long id);

    /**
     * ユーザーのタイムラインを公式APIから取得します
     * @since 1.0.0
     * @param user ユーザー
     * @return 取得したTL
     */
    MutableList<Tweet> getUserTimeline(User user);

    /**
     * ユーザーのタイムラインを公式APIから取得します
     * @since 1.0.0
     * @param id ユーザーID
     * @return 取得したTL
     */
    MutableList<Tweet> getUserTimeline(long id);

    /**
     * ユーザーのお気に入りを公式APIから取得します
     * @since 1.0.0
     * @param user ユーザー
     * @return 取得したお気に入り
     */
    MutableList<Tweet> getUserFavorites(User user);

    /**
     * ユーザーのお気に入りを公式APIから取得します
     * @since 1.0.0
     * @param id ユーザーID
     * @return 取得したお気に入り
     */
    MutableList<Tweet> getUserFavorites(long id);

    /**
     * フォロワーを取得します
     * @since 1.0.0
     * @param user ユーザー
     * @return 取得したフォロワー
     */
    MutableList<User> getFollowers(User user);

    /**
     * フォロワーを取得します
     * @since 1.0.0
     * @param id ユーザーID
     * @return 取得したフォロワー
     */
    MutableList<User> getFollowers(long id);

    /**
     * フォローしているユーザーを取得します
     * @since 1.0.0
     * @param user ユーザー
     * @return 取得したフォローしているユーザー
     */
    MutableList<User> getFriends(User user);

    /**
     * フォローしているユーザーを取得します
     * @since 1.0.0
     * @param id ユーザーID
     * @return 取得したフォローしているユーザー
     */
    MutableList<User> getFriends(long id);
}
