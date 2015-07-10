package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.Tweet;

public interface TweetResource {

    Tweet findTweet(long id);
}
