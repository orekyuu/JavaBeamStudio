package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.util.Callback;

import java.util.concurrent.CompletableFuture;

/**
 * ユーザーを操作するAPI
 * @since 1.0.0
 */
public interface UserControl {

    /**
     * フォロー状態を取得する
     * @deprecated {@link #fetchFollowStatus(User)}
     * @since 1.0.0
     * @param target 状態を取得したい対象
     * @return フォロー状態
     */
    @Deprecated
    FollowStatus checkFollowStatus(User target);

    /**
     * フォロー状態を取得する
     * @since 1.1.0
     * @param target 状態を取得したい対象
     * @return フォロー状態
     */
    CompletableFuture<FollowStatus> fetchFollowStatus(User target);

    /**
     * 非同期でフォロー状態を取得する
     * @deprecated {@link #fetchFollowStatus(User)}
     * @since 1.0.0
     * @param target 状態を取得したい対象
     * @param callback コールバック
     */
    @Deprecated
    void checkFollowStatusAsync(User target, Callback<FollowStatus> callback);

    /**
     * 第一引数のユーザーから見たtargetのフォロー状態を取得する
     * @deprecated {@link #fetchFollowStatus(User, User)}
     * @since 1.0.0
     * @param user user
     * @param target target
     * @return フォロー状態
     */
    @Deprecated
    FollowStatus checkFollowStatus(User user, User target);

    /**
     * 非同期で第一引数のユーザーから見たtargetのフォロー状態を取得する
     * @since 1.1.0
     * @param user user
     * @param target target
     * @return フォロー状態
     */
    CompletableFuture<FollowStatus> fetchFollowStatus(User user, User target);

    /**
     * 非同期で第一引数のユーザーから見たtargetのフォロー状態を取得する
     * @deprecated {@link #fetchFollowStatus(User, User)}
     * @since 1.0.0
     * @param user user
     * @param target target
     * @param callback コールバック
     */
    @Deprecated
    void checkFollowStatusAsync(User user, User target, Callback<FollowStatus> callback);

    /**
     * ターゲットをフォローします
     * @deprecated {@link #tryFollow(User)}
     * @since 1.0.0
     * @param target フォローする対象
     */
    @Deprecated
    void follow(User target);

    /**
     * ターゲットをフォローします
     * @since 1.1.0
     * @param target フォローする対象
     * @return フォローしたユーザー
     */
    CompletableFuture<User> tryFollow(User target);

    /**
     * ターゲットを非同期でフォローします
     * @deprecated {@link #tryFollow(User)}
     * @since 1.0.0
     * @param target フォローする対象
     */
    @Deprecated
    void followAsync(User target);

    /**
     * ターゲットのフォローを解除します
     * @deprecated {@link #tryUnfollow(User)}
     * @since 1.0.0
     * @param target フォロー解除する対象
     */
    @Deprecated
    void unfollow(User target);

    /**
     * ターゲットのフォローを解除します
     * @since 1.1.0
     * @param target フォロー解除する対象
     * @return フォロー解除したユーザー
     */
    CompletableFuture<User> tryUnfollow(User target);

    /**
     * ターゲットのフォローを非同期で解除します
     * @deprecated {@link #tryUnfollow(User)}
     * @since 1.0.0
     * @param target フォロー解除する対象
     */
    @Deprecated
    void unfollowAsync(User target);

    /**
     * ユーザーのプロファイルを更新します。
     * @deprecated {@link #tryUpdateProfile(String, String, String, String)}
     * @since 1.0.0
     * @param name ユーザー名
     * @param url WebサイトのURL
     * @param location 場所
     * @param description 説明
     */
    @Deprecated
    void updateProfile(String name, String url, String location, String description);

    /**
     * 非同期でユーザーのプロファイルを更新します。
     * @deprecated {@link #tryUpdateProfile(String, String, String, String)}
     * @since 1.0.0
     * @param name ユーザー名
     * @param url WebサイトのURL
     * @param location 場所
     * @param description 説明
     */
    @Deprecated
    void updateProfileAsync(String name, String url, String location, String description);

    /**
     * ユーザーのプロファイルを更新します。
     * @since 1.1.0
     * @param name ユーザー名
     * @param url WebサイトのURL
     * @param location 場所
     * @param description 説明
     * @return 更新後のUser
     */
    CompletableFuture<User> tryUpdateProfile(String name, String url, String location, String description);
}
