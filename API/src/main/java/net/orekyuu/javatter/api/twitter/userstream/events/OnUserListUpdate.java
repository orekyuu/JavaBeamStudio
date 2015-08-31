package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.model.UserList;

@FunctionalInterface
public interface OnUserListUpdate {
    void onUserListUpdate(User user, UserList userList);
}
