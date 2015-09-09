package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.model.UserList;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnUserListMemberAddition {
    /**
     * @since 1.0.0
     * @param user user
     * @param user1 user1
     * @param userList userList
     */
    void onUserListMemberAddition(User user, User user1, UserList userList);
}
