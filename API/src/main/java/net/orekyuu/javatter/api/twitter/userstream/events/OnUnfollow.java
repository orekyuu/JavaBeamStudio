package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnUnfollow {
    /**
     * @since 1.0.0
     * @param source source
     * @param unfollowedUser unfollowedUser
     */
    void onUnfollow(User source, User unfollowedUser);
}
