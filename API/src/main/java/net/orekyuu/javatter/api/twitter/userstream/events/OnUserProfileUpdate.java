package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnUserProfileUpdate {

    /**
     * @since 1.0.0
     * @param user user
     */
    void onUserProfileUpdate(User user);
}
