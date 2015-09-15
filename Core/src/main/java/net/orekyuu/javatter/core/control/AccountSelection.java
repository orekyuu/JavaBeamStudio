package net.orekyuu.javatter.core.control;

import com.sun.javafx.event.RedirectedEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PopupControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.User;

import java.util.List;
import java.util.function.Consumer;

public class AccountSelection extends PopupControl {

    private static final String DEFAULT_STYLE_CLASS = "account-selection";

    public AccountSelection(List<TwitterUser> users, Consumer<TwitterUser> selectedEvent) {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        setAutoHide(true);

        getScene().setRoot(createRootNode(users, selectedEvent));

        getScene().getWindow().setEventDispatcher((event, tail) -> {
            if (event.getEventType() == RedirectedEvent.REDIRECTED) {
                RedirectedEvent ev = (RedirectedEvent) event;
                if (ev.getOriginalEvent().getEventType() == MouseEvent.MOUSE_PRESSED) {
                    hide();
                }
            } else {
                tail.dispatchEvent(event);
            }
            return null;
        });
    }

    private Parent createRootNode(List<TwitterUser> users, Consumer<TwitterUser> selectedEvent) {
        HBox box = new HBox();
        for (TwitterUser twitterUser : users) {
            User user = twitterUser.getUser();
            Image image = new Image(user.getProfileImageURL(), true);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(64);
            imageView.setFitWidth(64);
            imageView.setOnMouseClicked(e -> {
                selectedEvent.accept(twitterUser);
                hide();
            });
            box.getChildren().add(imageView);
        }
        return box;
    }

    @Override
    public void hide() {
        if (!isShowing()) return;
        super.hide();
    }

    @Override
    public void show(Node anchor, double screenX, double screenY) {
        if (anchor == null) return;
        if (isShowing()) {
            hide();
        }
        super.show(anchor, screenX, screenY);
    }
}
