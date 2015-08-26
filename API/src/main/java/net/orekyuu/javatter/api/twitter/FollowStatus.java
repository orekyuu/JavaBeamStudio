package net.orekyuu.javatter.api.twitter;

/**
 * フォローの状態
 * @since 1.0.0
 */
public enum FollowStatus {
    /**
     * 無関心
     * @since 1.0.0
     */
    INDIFFERENCE,
    /**
     * フォローしている
     * @since 1.0.0
     */
    FOLLOW,
    /**
     * フォローされている
     * @since 1.0.0
     */
    FOLLOWER,
    /**
     * 相互フォロー
     * @since 1.0.0
     */
    FRIEND
}
