package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.Tweet;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnStatus {

    /**
     * @since 1.0.0
     * @param tweet 新規ツイート
     */
    void onStatus(Tweet tweet);
}
