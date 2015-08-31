package net.orekyuu.javatter.api.twitter.model;

import java.time.LocalDateTime;

public interface DirectMessage {

    long getId();

    String getText();

    LocalDateTime createdAt();

    User sender();

    User getRecipient();
}
