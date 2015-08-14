package net.orekyuu.javatter.api.notification;

import net.orekyuu.javatter.api.service.Service;

/**
 * 通知機能を提供するサービス
 */
@Service
public interface NotificationService {
    /**
     * 通知を作成するビルダーを作成します。
     * @since 1.0.0
     * @return NotificationBuilder
     */
    NotificationBuilder create();

    /**
     * 警告の通知を表示します。
     * @since 1.0.0
     * @param title タイトル
     * @param text テキスト
     */
    void showWarning(String title, String text);

    /**
     * 警告の通知を表示します。
     * @since 1.0.0
     * @param title タイトル
     * @param text テキスト
     * @param action クリック時のイベント
     */
    void showWarning(String title, String text, Runnable action);

    /**
     * エラー通知を表示します。
     * @since 1.0.0
     * @param title タイトル
     * @param text テキスト
     */
    void showError(String title, String text);

    /**
     * エラー通知を表示します。
     * @since 1.0.0
     * @param title タイトル
     * @param text テキスト
     * @param action クリック時のイベント
     */
    void showError(String title, String text, Runnable action);
}
