package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.userstream.UserStream;

public interface TwitterUser {

    void authentication(TwitterAccount account);

    TweetBuilder createTweet();

    UserStream userStream();

    User getUser();

    void dispose();
}
