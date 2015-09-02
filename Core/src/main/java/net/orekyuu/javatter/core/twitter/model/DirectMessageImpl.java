package net.orekyuu.javatter.core.twitter.model;

import net.orekyuu.javatter.api.twitter.model.DirectMessage;
import net.orekyuu.javatter.api.twitter.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DirectMessageImpl implements DirectMessage {

    private final long id;
    private final String text;
    private final LocalDateTime createdAt;
    private final User sender;
    private final User recipient;

    private DirectMessageImpl(twitter4j.DirectMessage message) {
        id = message.getId();
        text = message.getText();
        createdAt = LocalDateTime.ofInstant(message.getCreatedAt().toInstant(), ZoneId.systemDefault());
        sender = UserImpl.create(message.getSender());
        recipient = UserImpl.create(message.getRecipient());
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public LocalDateTime createdAt() {
        return createdAt;
    }

    @Override
    public User sender() {
        return sender;
    }

    @Override
    public User getRecipient() {
        return recipient;
    }

    public static DirectMessageImpl create(twitter4j.DirectMessage message) {
        return new DirectMessageImpl(message);
    }
}
