package net.orekyuu.javatter.api.twitter.userstream;

import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnMention {

    /**
     * @since 1.0.0
     * @param tweet tweet
     * @param from from
     */
    void onMention(Tweet tweet, User from);
}
