package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;

@FunctionalInterface
public interface OnFavorite {
    void onFavorite(User source, User target, Tweet tweet);
}
