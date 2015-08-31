package net.orekyuu.javatter.core.twitter.model;

import net.orekyuu.javatter.api.twitter.model.StatusDeletionNotice;

public class StatusDeletionNoticeImpl implements StatusDeletionNotice {
    private final long statusId;
    private final long userId;

    private StatusDeletionNoticeImpl(twitter4j.StatusDeletionNotice notice) {
        statusId = notice.getStatusId();
        userId = notice.getUserId();
    }

    @Override
    public long getStatusId() {
        return statusId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    public static StatusDeletionNotice create(twitter4j.StatusDeletionNotice statusDeletionNotice) {
        return new StatusDeletionNoticeImpl(statusDeletionNotice);
    }
}
