package net.orekyuu.javatter.api.twitter.model;

import net.orekyuu.javatter.api.twitter.TwitterUser;

import java.time.LocalDateTime;

public interface UserList {

    long getId();

    String getName();

    String getFullName();

    String getDescription();

    int getSubscriberCount();

    int getMemberCount();

    boolean isPublic();

    User getUser();

    boolean isFollowing(TwitterUser user);

    LocalDateTime getCreatedAt();
}
