package net.orekyuu.javatter.api.twitter.model;

import java.time.LocalDateTime;

/**
 * ダイレクトメッセージ
 */
public interface DirectMessage {

    /**
     * @since 1.0.0
     * @return id
     */
    long getId();

    /**
     * @since 1.0.0
     * @return 本文
     */
    String getText();

    /**
     * @since 1.0.0
     * @return 作成された時間
     */
    LocalDateTime createdAt();

    /**
     * @since 1.0.0
     * @return 発言したユーザー
     */
    User sender();

    /**
     * @since 1.0.0
     * @return 受け取ったユーザー
     */
    User getRecipient();
}
