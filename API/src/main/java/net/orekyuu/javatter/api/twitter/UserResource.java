package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.User;

public interface UserResource {

    User findUser(String screenName);

    User findUser(long id);
}
