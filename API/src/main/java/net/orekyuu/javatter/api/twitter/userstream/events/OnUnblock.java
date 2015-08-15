package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;

@FunctionalInterface
public interface OnUnblock {
    void onUnblock(User source, User unblockedUser);
}
