package net.orekyuu.javatter.api.twitter.userstream.events;

@FunctionalInterface
public interface OnException {

    void onException(Exception e);
}
