package net.orekyuu.javatter.api.twitter.model;

import java.time.LocalDateTime;

/**
 * ユーザーリスト
 */
public interface UserList {

    long getId();

    String getName();

    String getFullName();

    String getDescription();

    int getSubscriberCount();

    int getMemberCount();

    boolean isPublic();

    User getUser();

    LocalDateTime getCreatedAt();
}
