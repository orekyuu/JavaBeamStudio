package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.Tweet;

@FunctionalInterface
public interface OnStatus {

    void onStatus(Tweet tweet);
}
