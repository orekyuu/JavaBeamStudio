package net.orekyuu.javatter.core.action;

public enum EnumActionID {
    TWEET_ACTION("tweet"),
    COMMAND("command"),
    TWEET_OPEN_BROWSER("tweet_open"),
    FAV_RT("fav_rt"),
    TWEET_COPY("tweet_copy");

    private final String id;
    EnumActionID(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
