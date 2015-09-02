package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.StatusDeletionNotice;

@FunctionalInterface
public interface OnStatusDeletion {

    void onDelete(StatusDeletionNotice statusDeletionNotice);
}
