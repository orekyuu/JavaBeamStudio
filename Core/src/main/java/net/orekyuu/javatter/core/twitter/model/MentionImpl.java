package net.orekyuu.javatter.core.twitter.model;

import net.orekyuu.javatter.api.twitter.model.Mention;
import twitter4j.UserMentionEntity;

public class MentionImpl implements Mention {
    private final String name;
    private final String screenName;
    private final long id;

    public MentionImpl(UserMentionEntity e) {
        name = e.getName();
        screenName = e.getScreenName();
        id = e.getId();
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getId() {
        return id;
    }
}
