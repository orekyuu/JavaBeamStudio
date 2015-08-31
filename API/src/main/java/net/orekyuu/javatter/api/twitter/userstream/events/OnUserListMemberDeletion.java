package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.model.UserList;

@FunctionalInterface
public interface OnUserListMemberDeletion {
    void onUserListMemberDeletion(User user, User user1, UserList userList);
}
