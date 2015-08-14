package net.orekyuu.javatter.api.notification;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

/**
 * 通知を作成するビルダー
 */
public interface NotificationBuilder {

    /**
     * @since 1.0.0
     * @param text テキスト
     * @return メソッドチェーンのためのthis
     */
    NotificationBuilder text(String text);

    /**
     * @since 1.0.0
     * @param title タイトル
     * @return メソッドチェーンのためのthis
     */
    NotificationBuilder title(String title);

    /**
     * @since 1.0.0
     * @param icon アイコン
     * @return メソッドチェーンのためのthis
     */
    NotificationBuilder icon(Image icon);

    /**
     * @since 1.0.0
     * @param sound 表示時のサウンド
     * @return メソッドチェーンのためのthis
     */
    NotificationBuilder sound(AudioClip sound);

    /**
     * @since 1.0.0
     * @param runnable クリック時のイベント
     * @return メソッドチェーンのためのthis
     */
    NotificationBuilder setAction(Runnable runnable);

    /**
     * ポップアップを表示します
     * @since 1.0.0
     */
    void show();
}
