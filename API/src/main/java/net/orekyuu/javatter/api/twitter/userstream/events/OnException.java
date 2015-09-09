package net.orekyuu.javatter.api.twitter.userstream.events;

/**
 * @since 1.0.0
 */
@FunctionalInterface
public interface OnException {

    /**
     * @since 1.0.0
     * @param e e
     */
    void onException(Exception e);
}
