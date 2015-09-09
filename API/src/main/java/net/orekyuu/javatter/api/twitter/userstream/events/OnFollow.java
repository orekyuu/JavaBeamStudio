package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnFollow {
    /**
     * @since 1.0.0
     * @param source source
     * @param followedUser followedUser
     */
    void onFollow(User source, User followedUser);
}
