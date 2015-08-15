package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;

@FunctionalInterface
public interface OnUnfollow {
    void onUnfollow(User source, User unfollowedUser);
}
