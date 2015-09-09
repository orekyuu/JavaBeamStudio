package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnUnblock {
    /**
     * @since 1.0.0
     * @param source source
     * @param unblockedUser unblockedUser
     */
    void onUnblock(User source, User unblockedUser);
}
