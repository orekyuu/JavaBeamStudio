package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.model.UserList;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnUserListUpdate {
    /**
     * @since 1.0.0
     * @param user user
     * @param userList userList
     */
    void onUserListUpdate(User user, UserList userList);
}
