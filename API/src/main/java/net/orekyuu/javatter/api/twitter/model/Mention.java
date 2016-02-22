package net.orekyuu.javatter.api.twitter.model;

/**
 * ツイートなどに含まれるメンション
 * @since 1.0.2
 */
public interface Mention {

    /**
     * メンション先のユーザーのScreenName
     * @return ScreenName
     * @since 1.0.2
     */
    String getScreenName();

    /**
     * メンション先のユーザーの名前
     * @return Name
     * @since 1.0.2
     */
    String getName();

    /**
     * メンション先ユーザーのID
     * @return id
     * @since 1.0.2
     */
    long getId();
}
