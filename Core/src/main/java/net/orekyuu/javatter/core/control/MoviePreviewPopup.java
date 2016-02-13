package net.orekyuu.javatter.core.control;

import com.sun.javafx.event.RedirectedEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.PopupControl;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.util.Duration;

public class MoviePreviewPopup extends PopupControl {

    private MediaPlayer mediaPlayer;

    public MoviePreviewPopup() {
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

    public void showMovie(String url, Node anchor) {
        Media media = new Media(url);
        this.mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(this.mediaPlayer);
        this.mediaPlayer.setOnEndOfMedia(() -> {
            System.out.println("end");
            this.mediaPlayer.seek(Duration.ZERO);
            this.mediaPlayer.play();
        });
        mediaPlayer.setOnReady(this::moveToCenter);
        this.mediaPlayer.play();

        BorderPane pane = new BorderPane();
        pane.setPrefSize(400, 400);
        ProgressIndicator indicator = new ProgressIndicator(0);
        indicator.setMaxSize(150, 150);
        pane.setCenter(indicator);
        getScene().setRoot(pane);
        HBox top = new HBox();
        top.getChildren().add(mediaView);

        getScene().setRoot(top);

        show(anchor);
        moveToCenter();
    }

    @Override
    public void hide() {
        if (!isShowing()) return;
        mediaPlayer.dispose();
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
