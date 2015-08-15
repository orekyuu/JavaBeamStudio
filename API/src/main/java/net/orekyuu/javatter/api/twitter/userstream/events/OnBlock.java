package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;

@FunctionalInterface
public interface OnBlock {
    void onBlock(User source, User blockedUser);
}
