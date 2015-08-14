package net.orekyuu.javatter.api.twitter;

import com.gs.collections.api.list.MutableList;
import net.orekyuu.javatter.api.twitter.model.Tweet;

public interface TimelineResource {

    MutableList<Tweet> getHomeTimeline();
}
