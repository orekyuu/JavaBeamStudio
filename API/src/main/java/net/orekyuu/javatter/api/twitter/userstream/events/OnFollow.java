package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;

@FunctionalInterface
public interface OnFollow {
    void onFollow(User source, User followedUser);
}
