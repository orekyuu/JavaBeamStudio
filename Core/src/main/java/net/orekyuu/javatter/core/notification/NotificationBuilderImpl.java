package net.orekyuu.javatter.core.notification;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import net.orekyuu.javatter.api.notification.NotificationBuilder;
import org.controlsfx.control.Notifications;

public class NotificationBuilderImpl implements NotificationBuilder {

    private String text;
    private String title;
    private Image icon;
    private AudioClip sound;
    private Runnable runnable;

    @Override
    public NotificationBuilder text(String text) {
        this.text = text;
        return this;
    }

    @Override
    public NotificationBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public NotificationBuilder icon(Image icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public NotificationBuilder sound(AudioClip sound) {
        this.sound = sound;
        return this;
    }

    @Override
    public NotificationBuilder setAction(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    @Override
    public void popup() {
        Notifications notifications = Notifications.create();
        if (text != null) {
            notifications.text(text);
        }
        if (title != null) {
            notifications.title(title);
        }
        if (sound != null) {
            sound.play();
        }
        if (icon != null) {
            notifications.graphic(new ImageView(icon));
        }
        if (runnable != null) {
            notifications.onAction(e -> runnable.run());
        }
        notifications.position(Pos.BOTTOM_RIGHT);
        notifications.hideAfter(Duration.seconds(5));
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(notifications::show);
        } else {
            notifications.show();
        }
    }
}
