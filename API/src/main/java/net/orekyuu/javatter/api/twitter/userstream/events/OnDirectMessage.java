package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.DirectMessage;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnDirectMessage {
    /**
     * @since 1.0.0
     * @param directMessage directMessage
     */
    void onDirectMessage(DirectMessage directMessage);
}
