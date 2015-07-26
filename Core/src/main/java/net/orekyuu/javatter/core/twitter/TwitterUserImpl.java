package net.orekyuu.javatter.core.twitter;

import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.twitter.TweetBuilder;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.userstream.UserStream;
import net.orekyuu.javatter.core.cache.TweetCache;
import net.orekyuu.javatter.core.cache.UserCache;
import net.orekyuu.javatter.core.twitter.model.TweetImpl;
import net.orekyuu.javatter.core.twitter.model.UserImpl;
import net.orekyuu.javatter.core.twitter.userstream.UserStreamImpl;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Objects;
import java.util.Optional;
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
        thread.setName("AsyncTwitterActionThread");
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
//                stream.onException(ex);
                userStream.callException(ex);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
//                stream.onScrubGeo(userId, upToStatusId);
            }

            @Override
            public void onUserProfileUpdate(twitter4j.User updatedUser) {
//                stream.onUserProfileUpdate(updatedUser);
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//                stream.onDeletionNotice(statusDeletionNotice);
            }

            @Override
            public void onStatus(Status status) {
                logger.info(status.getText());
                Tweet tweet = TweetImpl.create(status);
                userStream.callStatus(tweet);
                long replyStatusId = tweet.getReplyStatusId();
                //リプライ先がなければおしまい
                if (replyStatusId == -1) {

                    return;
                }
                Tweet replyTo = findTweet(replyStatusId);
                if (replyTo.getOwner().getId() == getUser().getId()) {
                    userStream.callMentions(tweet);
                }
            }

            @Override
            public void onBlock(twitter4j.User source, twitter4j.User blockedUser) {
//                stream.onBlock(source, blockedUser);
            }

            @Override
            public void onFavorite(twitter4j.User source, twitter4j.User target, Status favoritedStatus) {
//                stream.onFavorite(source, target, favoritedStatus);
            }

            @Override
            public void onFollow(twitter4j.User source, twitter4j.User followedUser) {
//                stream.onFollow(source, followedUser);
            }

            @Override
            public void onUnblock(twitter4j.User source, twitter4j.User unblockedUser) {
//                stream.onUnblock(source, unblockedUser);
            }

            @Override
            public void onUnfollow(twitter4j.User source, twitter4j.User unfollowedUser) {
//                stream.onUnfollow(source, unfollowedUser);
            }

            @Override
            public void onUnfavorite(twitter4j.User source, twitter4j.User target, Status unfavoritedStatus) {
//                stream.onUnfavorite(source, target, unfavoritedStatus);
            }

            @Override
            public void onDirectMessage(DirectMessage directMessage) {
//                stream.onDirectMessage(directMessage);
            }

            @Override
            public void onUserListUpdate(twitter4j.User listOwner, UserList list) {
//                stream.onUserListUpdate(listOwner, list);
            }

            @Override
            public void onUserListSubscription(twitter4j.User subscriber, twitter4j.User listOwner, UserList list) {
//                stream.onUserListSubscription(subscriber, listOwner, list);
            }

            @Override
            public void onUserListUnsubscription(twitter4j.User subscriber, twitter4j.User listOwner, UserList list) {
//                stream.onUserListUnsubscription(subscriber, listOwner, list);
            }

            @Override
            public void onUserListMemberAddition(twitter4j.User addedMember, twitter4j.User listOwner, UserList list) {
//                stream.onUserListMemberAddition(addedMember, listOwner, list);
            }

            @Override
            public void onUserListMemberDeletion(twitter4j.User deletedMember, twitter4j.User listOwner, UserList list) {
//                stream.onUserListMemberDeletion(deletedMember, listOwner, list);
            }

            @Override
            public void onUserListDeletion(twitter4j.User listOwner, UserList list) {
//                stream.onUserListDeletion(listOwner, list);
            }

            @Override
            public void onUserListCreation(twitter4j.User listOwner, UserList list) {
//                stream.onUserListCreation(listOwner, list);
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
        twitterStream.shutdown();
        logger.fine("user stream shutdown.");
    }

    @Override
    public void favorite(Tweet tweet) {
        try {
            twitter.createFavorite(tweet.getStatusId());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void favoriteAsync(Tweet tweet) {
        tweetActionExecutor.execute(() -> favorite(tweet));
    }

    @Override
    public void unFavorite(Tweet tweet) {
        try {
            twitter.destroyFavorite(tweet.getStatusId());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unFavoriteAsync(Tweet tweet) {
        tweetActionExecutor.execute(() -> unFavorite(tweet));
    }

    @Override
    public void reTweet(Tweet tweet) {
        try {
            twitter.retweetStatus(tweet.getStatusId());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reTweetAsync(Tweet tweet) {
        tweetActionExecutor.execute(() -> reTweet(tweet));
    }

    @Override
    public void removeTweet(Tweet tweet) {
        try {
            twitter.destroyStatus(tweet.getStatusId());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeTweetAsync(Tweet tweet) {
        tweetActionExecutor.execute(() -> removeTweet(tweet));
    }

    @Override
    public Tweet findTweet(long id) {
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUser(String screenName) {
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUser(long id) {
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
            e.printStackTrace();
        }
        return null;
    }
}
