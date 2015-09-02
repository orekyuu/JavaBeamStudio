package net.orekyuu.javatter.api.twitter.userstream.events;

import net.orekyuu.javatter.api.twitter.model.DirectMessage;

@FunctionalInterface
public interface OnDirectMessage {
    void onDirectMessage(DirectMessage directMessage);
}
