package net.orekyuu.javatter.core.control;

import com.sun.javafx.event.RedirectedEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PopupControl;
import javafx.scene.input.MouseEvent;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;

import java.io.IOException;

public class AboutPopup extends PopupControl {

    public AboutPopup() {
        JavatterFXMLLoader fxmlLoader = new JavatterFXMLLoader(getClass()
                .getResource("/layout/about.fxml"));
        try {
            Parent parent = fxmlLoader.load();
            getScene().setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    @Override
    public void hide() {
        if (!isShowing()) return;
        super.hide();
    }

    public void show(Node anchor) {
        if (anchor == null) return;
        if (isShowing()) {
            hide();
        }
        super.show(anchor, 0, 0);
        centerOnScreen();
    }
}
