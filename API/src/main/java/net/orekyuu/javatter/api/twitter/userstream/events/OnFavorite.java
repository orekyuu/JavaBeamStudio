package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnFavorite {
    /**
     * @since 1.0.0
     * @param source source
     * @param target target
     * @param tweet tweet
     */
    void onFavorite(User source, User target, Tweet tweet);
}
