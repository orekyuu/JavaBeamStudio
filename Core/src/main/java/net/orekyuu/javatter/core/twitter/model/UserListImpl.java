package net.orekyuu.javatter.core.twitter.model;

import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.model.UserList;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserListImpl implements UserList {

    private final long id;
    private final String name;
    private final String fullName;
    private final String description;
    private final int subscriberCount;
    private final int memberCount;
    private final LocalDateTime createdAt;
    private final boolean aPublic;
    private final User user;

    private UserListImpl(twitter4j.UserList userList) {
        id = userList.getId();
        name = userList.getName();
        fullName = userList.getFullName();
        description = userList.getDescription();
        subscriberCount = userList.getSubscriberCount();
        memberCount = userList.getMemberCount();
        aPublic = userList.isPublic();
        createdAt = LocalDateTime.ofInstant(userList.getCreatedAt().toInstant(), ZoneId.systemDefault());
        user = UserImpl.create(userList.getUser());
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getSubscriberCount() {
        return subscriberCount;
    }

    @Override
    public int getMemberCount() {
        return memberCount;
    }

    @Override
    public boolean isPublic() {
        return aPublic;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean isFollowing(TwitterUser user) {
        return false;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static UserList create(twitter4j.UserList userList) {
        return new UserListImpl(userList);
    }
}
