package net.orekyuu.javatter.core.notification;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.util.Duration;
import net.orekyuu.javatter.api.notification.NotificationBuilder;
import net.orekyuu.javatter.api.notification.NotificationService;
import org.controlsfx.control.Notifications;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public NotificationBuilder create() {
        return new NotificationBuilderImpl();
    }

    @Override
    public void showWarning(String title, String text) {
        showWarning(title, text, null);
    }

    @Override
    public void showWarning(String title, String text, Runnable action) {
        Notifications notifications = Notifications.create().position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(5))
                .title(title).text(text);
        if (action != null) {
            notifications.onAction(e -> action.run());
        }
        if (Platform.isFxApplicationThread()) {
            notifications.showWarning();
        } else {
            Platform.runLater(notifications::showWarning);
        }
    }

    @Override
    public void showError(String title, String text) {
        showError(title, text, null);
    }

    @Override
    public void showError(String title, String text, Runnable action) {
        Notifications notifications = Notifications.create().position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(5))
                .title(title).text(text);
        if (action != null) {
            notifications.onAction(e -> action.run());
        }
        if (Platform.isFxApplicationThread()) {
            notifications.showError();
        } else {
            Platform.runLater(notifications::showError);
        }
    }
}
