package net.orekyuu.javatter.api.twitter.model;

import java.time.LocalDateTime;

public interface User {
    LocalDateTime getCreatedAt();

    String getDescription();

    int getFavCount();

    int getFollowersCount();

    int getFriendsCount();

    long getId();

    int getListedCount();

    String getLocation();

    String getName();

    String getScreenName();

    String getProfileImageURL();

    String getOriginalProfileImageURL();

    String getProfileBackgroundImageURL();

    int getTweetCount();

    String getWebSite();
}
