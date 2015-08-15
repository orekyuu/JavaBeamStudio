package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;

@FunctionalInterface
public interface OnUnfavorite {
    void onUnfavorite(User source, User target, Tweet unfavoritedTweet);
}
