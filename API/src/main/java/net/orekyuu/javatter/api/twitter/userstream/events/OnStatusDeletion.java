package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.StatusDeletionNotice;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnStatusDeletion {

    /**
     * @since 1.0.0
     * @param statusDeletionNotice statusDeletionNotice
     */
    void onDelete(StatusDeletionNotice statusDeletionNotice);
}
