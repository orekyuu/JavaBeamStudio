package net.orekyuu.javatter.core.control;

import com.sun.javafx.event.RedirectedEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.PopupControl;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

public class ImagePreviewPopup extends PopupControl {


    public ImagePreviewPopup() {

        setAutoFix(true);
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
        centerOnScreen();
    }

    public void showImage(String url, Node anchor) {
        Image image = new Image(url, true);
        BorderPane pane = new BorderPane();
        pane.setPrefSize(400, 400);
        ProgressIndicator indicator = new ProgressIndicator(0);
        indicator.setMaxSize(150, 150);
        pane.setCenter(indicator);
        getScene().setRoot(pane);
        image.progressProperty().addListener((observable, oldValue, newValue) -> {
            if (1.0 <= newValue.doubleValue()) {
                ImageView imageView = new ImageView(image);
                HBox top = new HBox();
                top.getChildren().add(imageView);

                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

                double w = visualBounds.getWidth() / image.getWidth();
                double h = visualBounds.getHeight() / image.getHeight();
                double a = Math.min(1.0, Math.min(w, h)) * 0.9;

                imageView.setFitWidth(image.getWidth() * a);
                imageView.setFitHeight(image.getHeight() * a);

                getScene().setRoot(top);
                moveToCenter();
            } else {
                indicator.setProgress(newValue.doubleValue());
            }
        });

        show(anchor);
        moveToCenter();
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
    }

    private void moveToCenter() {
        Rectangle2D bounds = getScreen().getVisualBounds();
        double centerX = (bounds.getWidth() - getWidth()) / 2;
        double centerY = (bounds.getHeight() - getHeight()) / 2;
        setX(centerX);
        setY(centerY);
    }

    private Screen getScreen() {
        return Screen.getPrimary();
    }
}
