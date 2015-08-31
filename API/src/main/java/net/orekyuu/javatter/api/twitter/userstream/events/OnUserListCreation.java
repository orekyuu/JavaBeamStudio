package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.model.UserList;

@FunctionalInterface
public interface OnUserListCreation {
    void onUserListCreation(User user, UserList userList);
}
