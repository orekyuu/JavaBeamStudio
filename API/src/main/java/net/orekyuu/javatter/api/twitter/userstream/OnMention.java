package net.orekyuu.javatter.api.twitter.userstream;

import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;

public interface OnMention {

    void onMention(Tweet tweet, User from);
}
