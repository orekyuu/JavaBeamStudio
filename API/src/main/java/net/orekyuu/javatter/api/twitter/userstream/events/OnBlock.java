package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnBlock {
    /**
     * @since 1.0.0
     * @param source source
     * @param blockedUser blockedUser
     */
    void onBlock(User source, User blockedUser);
}
