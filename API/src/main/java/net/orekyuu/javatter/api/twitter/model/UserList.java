package net.orekyuu.javatter.api.twitter.model;

import java.time.LocalDateTime;

/**
 * ユーザーリスト
 * @since 1.0.0
 */
public interface UserList {

    /**
     * @since 1.0.0
     * @return id
     */
    long getId();

    /**
     * @since 1.0.0
     * @return name
     */
    String getName();

    /**
     * @since 1.0.0
     * @return fullName
     */
    String getFullName();

    /**
     * @since 1.0.0
     * @return description
     */
    String getDescription();

    /**
     * @since 1.0.0
     * @return subscriberCount
     */
    int getSubscriberCount();

    /**
     * @since 1.0.0
     * @return memberCount
     */
    int getMemberCount();

    /**
     * @since 1.0.0
     * @return isPublic
     */
    boolean isPublic();

    /**
     * @since 1.0.0
     * @return user
     */
    User getUser();

    /**
     * @since 1.0.0
     * @return createdAt
     */
    LocalDateTime getCreatedAt();
}
