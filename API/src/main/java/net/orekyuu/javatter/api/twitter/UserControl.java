package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.util.Callback;

/**
 * ユーザーを操作するAPI
 */
public interface UserControl {

    /**
     * フォロー状態を取得する
     * @since 1.0.0
     * @param target 状態を取得したい対象
     * @return フォロー状態
     */
    FollowStatus checkFollowStatus(User target);

    /**
     * 非同期でフォロー状態を取得する
     * @since 1.0.0
     * @param target 状態を取得したい対象
     * @param callback コールバック
     */
    void checkFollowStatusAsync(User target, Callback<FollowStatus> callback);

    /**
     * 第一引数のユーザーから見たtargetのフォロー状態を取得する
     * @since 1.0.0
     * @param user user
     * @param target target
     * @return フォロー状態
     */
    FollowStatus checkFollowStatus(User user, User target);

    /**
     * 非同期で第一引数のユーザーから見たtargetのフォロー状態を取得する
     * @since 1.0.0
     * @param user user
     * @param target target
     * @param callback コールバック
     */
    void checkFollowStatusAsync(User user, User target, Callback<FollowStatus> callback);

    /**
     * ターゲットをフォローします
     * @since 1.0.0
     * @param target フォローする対象
     */
    void follow(User target);

    /**
     * ターゲットを非同期でフォローします
     * @since 1.0.0
     * @param target フォローする対象
     */
    void followAsync(User target);

    /**
     * ターゲットのフォローを解除します
     * @since 1.0.0
     * @param target フォロー解除する対象
     */
    void unfollow(User target);

    /**
     * ターゲットのフォローを非同期で解除します
     * @since 1.0.0
     * @param target フォロー解除する対象
     */
    void unfollowAsync(User target);
}
