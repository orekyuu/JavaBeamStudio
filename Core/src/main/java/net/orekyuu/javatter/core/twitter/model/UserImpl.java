package net.orekyuu.javatter.core.twitter.model;

import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.core.cache.UserCache;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserImpl implements User {

    private final LocalDateTime createdAt;
    private final String description;
    private final int favCount;
    private final int followersCount;
    private final int friendsCount;
    private final int tweetCount;
    private final long id;
    private final int listedCount;
    private final String location;
    private final String webSite;
    private final String name;
    private final String screenName;
    private final String profileImageURL;

    private UserImpl(twitter4j.User user) {
        createdAt = LocalDateTime.ofInstant(user.getCreatedAt().toInstant(), ZoneId.systemDefault());
        description = user.getDescription();
        favCount = user.getFavouritesCount();
        followersCount = user.getFollowersCount();
        friendsCount = user.getFriendsCount();
        tweetCount = user.getStatusesCount();
        id = user.getId();
        listedCount = user.getListedCount();
        location = user.getLocation();
        webSite = user.getURL();
        name = user.getName();
        screenName = user.getScreenName();
        profileImageURL = user.getProfileImageURL();
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getFavCount() {
        return favCount;
    }

    @Override
    public int getFollowersCount() {
        return followersCount;
    }

    @Override
    public int getFriendsCount() {
        return friendsCount;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int getListedCount() {
        return listedCount;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public String getProfileImageURL() {
        return profileImageURL;
    }

    @Override
    public int getTweetCount() {
        return tweetCount;
    }

    @Override
    public String getWebSite() {
        return webSite;
    }

    public static User create(twitter4j.User user) {
        UserImpl userImpl = new UserImpl(user);
        UserCache.getInstance().update(userImpl);
        return userImpl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserImpl{");
        sb.append("createdAt=").append(createdAt);
        sb.append(", description='").append(description).append('\'');
        sb.append(", favCount=").append(favCount);
        sb.append(", followersCount=").append(followersCount);
        sb.append(", friendsCount=").append(friendsCount);
        sb.append(", tweetCount=").append(tweetCount);
        sb.append(", id=").append(id);
        sb.append(", listedCount=").append(listedCount);
        sb.append(", location='").append(location).append('\'');
        sb.append(", webSite='").append(webSite).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", screenName='").append(screenName).append('\'');
        sb.append(", profileImageURL='").append(profileImageURL).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
