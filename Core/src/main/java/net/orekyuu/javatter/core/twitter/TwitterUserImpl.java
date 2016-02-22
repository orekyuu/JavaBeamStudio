package net.orekyuu.javatter.core.twitter;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.twitter.FollowStatus;
import net.orekyuu.javatter.api.twitter.TweetBuilder;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.userstream.UserStream;
import net.orekyuu.javatter.api.util.Callback;
import net.orekyuu.javatter.core.cache.FavoriteCache;
import net.orekyuu.javatter.core.cache.RetweetCache;
import net.orekyuu.javatter.core.cache.TweetCache;
import net.orekyuu.javatter.core.cache.UserCache;
import net.orekyuu.javatter.core.twitter.model.*;
import net.orekyuu.javatter.core.twitter.model.StatusDeletionNoticeImpl;
import net.orekyuu.javatter.core.twitter.userstream.UserStreamImpl;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TwitterUserImpl implements TwitterUser {

    private Twitter twitter;
    private TwitterStream twitterStream;
    private UserStreamImpl userStream = new UserStreamImpl();
    private static final Logger logger = Logger.getLogger(TwitterUserImpl.class.getName());
    private User user;

    private static final ExecutorService tweetActionExecutor = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setName("AsyncTweetActionThread");
        thread.setDaemon(true);
        return thread;
    });
    private static final ExecutorService userActionExecutor = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setName("AsyncUserActionThread");
        thread.setDaemon(true);
        return thread;
    });

    @Override
    public void authentication(TwitterAccount account) {
        Objects.requireNonNull(account);

        AccessToken token = new AccessToken(account.getToken(), account.getTokenSecret());
        ConfigurationBuilder conf = new ConfigurationBuilder()
                .setOAuthConsumerKey("rMvLmU5qMgbZwg92Is5g")
                .setOAuthConsumerSecret("RD28Uuu44KeMOs90UuqXAAoVTWXRTmD4H8xYKZSgBk")
                .setOAuthAccessToken(token.getToken())
                .setOAuthAccessTokenSecret(token.getTokenSecret());
        twitter = new TwitterFactory(conf.build()).getInstance();
        logger.fine("認証成功: " + account);
        try {
            user = UserImpl.create(twitter.showUser(token.getUserId()));
        } catch (TwitterException e) {
            e.printStackTrace();
            logger.warning(e.toString());
        }

        UserStreamAdapter adapter = new UserStreamAdapter() {
            @Override
            public void onException(Exception ex) {
                userStream.callException(ex);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                userStream.callScrubGeo(userId, upToStatusId);
            }

            @Override
            public void onUserProfileUpdate(twitter4j.User updatedUser) {
                userStream.callUserProfileUpdate(UserImpl.create(updatedUser));
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                userStream.callDeletionNotice(StatusDeletionNoticeImpl.create(statusDeletionNotice));
            }

            @Override
            public void onStatus(Status status) {
                logger.info(status.getText());
                //ツイートイベントの発火
                Tweet tweet = TweetImpl.create(status);
                userStream.callStatus(tweet);
                //リツイート状態の更新
                RetweetCache.getInstance().update(TwitterUserImpl.this, tweet, status.isRetweetedByMe());
                //お気に入り状態の更新
                FavoriteCache.getInstance().update(TwitterUserImpl.this, tweet, status.isFavorited());

                if (tweet.mentions().anySatisfy(m -> m.getId() == getUser().getId())
                        && !status.isRetweet()) {
                    userStream.callMentions(tweet);
                }
            }

            @Override
            public void onBlock(twitter4j.User source, twitter4j.User blockedUser) {
                userStream.callBlock(UserImpl.create(source), UserImpl.create(blockedUser));
            }

            @Override
            public void onFavorite(twitter4j.User source, twitter4j.User target, Status favoritedStatus) {
                //ふぁぼったのが自分ならツイートのふぁぼ状況を更新
                Tweet tweet = TweetImpl.create(favoritedStatus);
                if (source.getId() == getUser().getId()) {
                    FavoriteCache.getInstance().update(TwitterUserImpl.this, tweet, true);
                }
                userStream.callFavorite(UserImpl.create(source), UserImpl.create(target), tweet);
            }

            @Override
            public void onFollow(twitter4j.User source, twitter4j.User followedUser) {
                userStream.callFollow(UserImpl.create(source), UserImpl.create(followedUser));
            }

            @Override
            public void onUnblock(twitter4j.User source, twitter4j.User unblockedUser) {
                userStream.callUnblock(UserImpl.create(source), UserImpl.create(unblockedUser));
            }

            @Override
            public void onUnfollow(twitter4j.User source, twitter4j.User unfollowedUser) {
                userStream.callUnfollow(UserImpl.create(source), UserImpl.create(unfollowedUser));
            }

            @Override
            public void onUnfavorite(twitter4j.User source, twitter4j.User target, Status unfavoritedStatus) {
                //あんふぁぼったのが自分ならツイートのふぁぼ状況を更新
                Tweet tweet = TweetImpl.create(unfavoritedStatus);
                if (source.getId() == getUser().getId()) {
                    FavoriteCache.getInstance().update(TwitterUserImpl.this, tweet, false);
                }
                userStream.callUnfavorite(UserImpl.create(source), UserImpl.create(target), tweet);
            }

            @Override
            public void onDirectMessage(DirectMessage directMessage) {
                userStream.callDirectMessage(DirectMessageImpl.create(directMessage));
            }

            @Override
            public void onUserListUpdate(twitter4j.User listOwner, UserList list) {
                userStream.callUserListUpdate(UserImpl.create(listOwner), UserListImpl.create(list));
            }

            @Override
            public void onUserListSubscription(twitter4j.User subscriber, twitter4j.User listOwner, UserList list) {
                userStream.callUserListSubscription(UserImpl.create(subscriber), UserImpl.create(listOwner), UserListImpl.create(list));
            }

            @Override
            public void onUserListUnsubscription(twitter4j.User subscriber, twitter4j.User listOwner, UserList list) {
                userStream.callUserListUnsubscription(UserImpl.create(subscriber), UserImpl.create(listOwner), UserListImpl.create(list));
            }

            @Override
            public void onUserListMemberAddition(twitter4j.User addedMember, twitter4j.User listOwner, UserList list) {
                userStream.callUserListMemberAddition(UserImpl.create(addedMember), UserImpl.create(listOwner), UserListImpl.create(list));
            }

            @Override
            public void onUserListMemberDeletion(twitter4j.User deletedMember, twitter4j.User listOwner, UserList list) {
                userStream.callUserListMemberDeletion(UserImpl.create(deletedMember), UserImpl.create(listOwner), UserListImpl.create(list));
            }


            @Override
            public void onUserListDeletion(twitter4j.User listOwner, UserList list) {
                userStream.callUserListDeletion(UserImpl.create(listOwner), UserListImpl.create(list));
            }

            @Override
            public void onUserListCreation(twitter4j.User listOwner, UserList list) {
                userStream.callUserListCreation(UserImpl.create(listOwner), UserListImpl.create(list));
            }
        };

        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(adapter);
        twitterStream.setOAuthConsumer("rMvLmU5qMgbZwg92Is5g", "RD28Uuu44KeMOs90UuqXAAoVTWXRTmD4H8xYKZSgBk");
        twitterStream.setOAuthAccessToken(token);
        String sampleStream = System.getProperty("sampleStream");
        if (sampleStream != null && Boolean.valueOf(sampleStream)) {
            logger.info("sampleStream enabled.");
            twitterStream.sample();
        } else {
            twitterStream.user();
        }
    }

    @Override
    public TweetBuilder createTweet() {
        return new TweetBuilderImpl(twitter);
    }

    @Override
    public UserStream userStream() {
        return userStream;
    }

    @Override
    public net.orekyuu.javatter.api.twitter.model.User getUser() {
        return user;
    }

    @Override
    public void dispose() {
        logger.info("user stream shutdown start.");
        twitterStream.shutdown();
        logger.info("user stream shutdown finished.");
    }

    @Override
    public void favorite(Tweet tweet) {
        try {
            tryFavorite(tweet).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void favoriteAsync(Tweet tweet) {
        try {
            tryFavorite(tweet);
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unFavorite(Tweet tweet) {
        try {
            tryUnfavorite(tweet).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unFavoriteAsync(Tweet tweet) {
        try {
            tryUnfavorite(tweet);
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retweet(Tweet tweet) {
        try {
            tryRetweet(tweet).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void retweetAsync(Tweet tweet) {
        try {
            tryRetweet(tweet);
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeTweet(Tweet tweet) {
        try {
            deleteTweet(tweet).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeTweetAsync(Tweet tweet) {
        try {
            deleteTweet(tweet);
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Tweet findTweet(long id) {
        try {
            findTweetById(id).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUser(String screenName) {
        try {
            return findUserByScreenName(screenName).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUser(long id) {
        try {
            return findUserById(id).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MutableList<Tweet> getUserTimeline(User user) {
        try {
            return fetchTimeline(user).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return Lists.mutable.empty();
    }

    @Override
    public MutableList<Tweet> getUserTimeline(long id) {
        try {
            return fetchTimeline(id).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return Lists.mutable.empty();
    }

    @Override
    public MutableList<Tweet> getUserFavorites(User user) {
        try {
            return fetchFavorites(user).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return Lists.mutable.empty();
    }

    @Override
    public MutableList<Tweet> getUserFavorites(long id) {
        try {
            return fetchFavorites(id).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return Lists.mutable.empty();
    }

    @Override
    public MutableList<User> getFollowers(User user) {
        try {
            return fetchFollowers(user).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return Lists.mutable.empty();
    }

    @Override
    public MutableList<User> getFollowers(long id) {
        try {
            return fetchFollowers(id).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return Lists.mutable.empty();
    }

    @Override
    public MutableList<User> getFriends(User user) {
        try {
            return fetchFriends(user).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return Lists.mutable.empty();
    }

    @Override
    public MutableList<User> getFriends(long id) {
        try {
            return fetchFriends(id).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return Lists.mutable.empty();
    }

    @Override
    public MutableList<Tweet> getHomeTimeline() {
        try {
            return fetchHomeTimeline().join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return Lists.mutable.empty();
    }

    @Override
    public MutableList<Tweet> getMentions() {
        try {
            return fetchMentions().join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return Lists.mutable.empty();
    }

    @Override
    public FollowStatus checkFollowStatus(User target) {
        try {
            return checkFollowStatus(getUser(), target);
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return FollowStatus.INDIFFERENCE;
    }

    @Override
    public void checkFollowStatusAsync(User target, Callback<FollowStatus> callback) {
        try {
            checkFollowStatusAsync(getUser(), target, callback);
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FollowStatus checkFollowStatus(User user, User target) {
        try {
            return fetchFollowStatus(user, target).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
        return FollowStatus.INDIFFERENCE;
    }

    @Override
    public void checkFollowStatusAsync(User user, User target, Callback<FollowStatus> callback) {
        fetchFollowStatus(user, user).thenAccept(callback::result);
    }

    @Override
    public void follow(User target) {
        try {
            tryFollow(target).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void followAsync(User target) {
        try {
            tryFollow(target);
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unfollow(User target) {
        try {
            tryUnfollow(target).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unfollowAsync(User target) {
        try {
            tryUnfollow(target);
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfile(String name, String url, String location, String description) {
        try {
            tryUpdateProfile(name, url, location, description).join();
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfileAsync(String name, String url, String location, String description) {
        try {
            tryUpdateProfile(name, url, location, description);
        } catch (net.orekyuu.javatter.api.twitter.TwitterException e) {
            e.printStackTrace();
        }
    }

    //<editor-fold desc="API1.1.0 methods">
    //<editor-fold desc="TweetControl">
    @Override
    public CompletableFuture<Tweet> deleteTweet(Tweet tweet) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Status status = twitter.destroyStatus(tweet.getStatusId());
                return TweetImpl.create(status);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        });
    }
    //</editor-fold>

    //<editor-fold desc="TweetResource">
    @Override
    public CompletableFuture<Tweet> findTweetById(long id) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<Tweet> byId = TweetCache.getInstance().getById(id);
            if (byId.isPresent()) {
                return byId.get();
            }

            try {
                Status status = twitter.showStatus(id);
                Tweet tweet = TweetImpl.create(status);
                TweetCache.getInstance().update(tweet);
                return tweet;
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        }, tweetActionExecutor);
    }
    //</editor-fold>

    //<editor-fold desc="RetweetControl">
    @Override
    public CompletableFuture<Tweet> tryRetweet(Tweet tweet) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Status status = twitter.retweetStatus(tweet.getStatusId());
                return TweetImpl.create(status);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        }, tweetActionExecutor);
    }
    //</editor-fold>

    //<editor-fold desc="TimelineResource">
    @Override
    public CompletableFuture<MutableList<Tweet>> fetchHomeTimeline() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseList<Status> homeTimeline = twitter.getHomeTimeline();
                return Lists.mutable.ofAll(homeTimeline).collect(TweetImpl::create);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        }, tweetActionExecutor);
    }
    //</editor-fold>

    //<editor-fold desc="MentionsResource">
    @Override
    public CompletableFuture<MutableList<Tweet>> fetchMentions() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseList<Status> statuses = twitter.getMentionsTimeline();
                return Lists.mutable.ofAll(statuses).collect(TweetImpl::create);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        }, tweetActionExecutor);
    }
    //</editor-fold>

    //<editor-fold desc="FavoriteControl">
    @Override
    public CompletableFuture<Tweet> tryFavorite(Tweet tweet) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Status favorite = twitter.createFavorite(tweet.getStatusId());
                return TweetImpl.create(favorite);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        }, tweetActionExecutor);
    }

    @Override
    public CompletableFuture<Tweet> tryUnfavorite(Tweet tweet) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Status favorite = twitter.destroyFavorite(tweet.getStatusId());
                return TweetImpl.create(favorite);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        }, tweetActionExecutor);
    }
    //</editor-fold>

    //<editor-fold desc="UserControl">
    @Override
    public CompletableFuture<FollowStatus> fetchFollowStatus(User target) {
        return fetchFollowStatus(getUser(), target);
    }

    @Override
    public CompletableFuture<FollowStatus> fetchFollowStatus(User user, User target) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Relationship friendship = twitter.showFriendship(user.getId(), target.getId());
                if (friendship.isSourceFollowedByTarget() && friendship.isSourceFollowingTarget()) {
                    return FollowStatus.FRIEND;
                } else if (friendship.isSourceFollowedByTarget()) {
                    return FollowStatus.FOLLOWER;
                } else if (friendship.isSourceFollowingTarget()) {
                    return FollowStatus.FOLLOW;
                } else {
                    return FollowStatus.INDIFFERENCE;
                }
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        }, userActionExecutor);
    }

    @Override
    public CompletableFuture<User> tryFollow(User target) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                twitter4j.User user = twitter.createFriendship(target.getId());
                return UserImpl.create(user);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        }, userActionExecutor);
    }

    @Override
    public CompletableFuture<User> tryUnfollow(User target) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                twitter4j.User user = twitter.destroyFriendship(target.getId());
                return UserImpl.create(user);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        }, userActionExecutor);
    }

    @Override
    public CompletableFuture<User> tryUpdateProfile(String name, String url, String location, String description) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                twitter4j.User user = twitter.updateProfile(name, url, location, description);
                return UserImpl.create(user);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        }, userActionExecutor);
    }
    //</editor-fold>

    //<editor-fold desc="UserResource">
    @Override
    public CompletableFuture<User> findUserByScreenName(String screenName) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<User> byName = UserCache.getInstance().getByName(screenName);
            if (byName.isPresent()) {
                return byName.get();
            }

            try {
                twitter4j.User showUser = twitter.showUser(screenName);
                User user = UserImpl.create(showUser);
                UserCache.getInstance().update(user);
                return user;
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        });
    }

    @Override
    public CompletableFuture<User> findUserById(long id) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<User> byName = UserCache.getInstance().getById(id);
            if (byName.isPresent()) {
                return byName.get();
            }

            try {
                twitter4j.User showUser = twitter.showUser(id);
                User user = UserImpl.create(showUser);
                UserCache.getInstance().update(user);
                return user;
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        });
    }

    @Override
    public CompletableFuture<MutableList<Tweet>> fetchTimeline(User user) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseList<Status> timeline = twitter.getUserTimeline(user.getId());
                return Lists.mutable.ofAll(timeline).collect(TweetImpl::create);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        });
    }

    @Override
    public CompletableFuture<MutableList<Tweet>> fetchTimeline(long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseList<Status> timeline = twitter.getUserTimeline(id);
                return Lists.mutable.ofAll(timeline).collect(TweetImpl::create);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        });
    }

    @Override
    public CompletableFuture<MutableList<Tweet>> fetchFavorites(User user) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseList<Status> favorites = twitter.getFavorites(user.getId());
                return Lists.mutable.ofAll(favorites).collect(TweetImpl::create);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        });
    }

    @Override
    public CompletableFuture<MutableList<Tweet>> fetchFavorites(long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseList<Status> favorites = twitter.getFavorites(id);
                return Lists.mutable.ofAll(favorites).collect(TweetImpl::create);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        });
    }

    @Override
    public CompletableFuture<MutableList<User>> fetchFollowers(User user) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PagableResponseList<twitter4j.User> followersList = twitter.getFollowersList(user.getId(), -1);
                return Lists.mutable.ofAll(followersList).collect(UserImpl::create);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        });
    }

    @Override
    public CompletableFuture<MutableList<User>> fetchFollowers(long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PagableResponseList<twitter4j.User> followersList = twitter.getFollowersList(id, -1);
                return Lists.mutable.ofAll(followersList).collect(UserImpl::create);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        });
    }

    @Override
    public CompletableFuture<MutableList<User>> fetchFriends(User user) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PagableResponseList<twitter4j.User> followersList = twitter.getFriendsList(user.getId(), -1);
                return Lists.mutable.ofAll(followersList).collect(UserImpl::create);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        });
    }

    @Override
    public CompletableFuture<MutableList<User>> fetchFriends(long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PagableResponseList<twitter4j.User> followersList = twitter.getFriendsList(id, -1);
                return Lists.mutable.ofAll(followersList).collect(UserImpl::create);
            } catch (TwitterException e) {
                throw new net.orekyuu.javatter.api.twitter.TwitterException();
            }
        });
    }
    //</editor-fold>
    //</editor-fold>
}
