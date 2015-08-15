package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;

public interface OnUserProfileUpdate {

    void onUserProfileUpdate(User user);
}
