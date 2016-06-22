package net.orekyuu.javatter.core.control;

import com.sun.javafx.event.RedirectedEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PopupControl;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.util.Duration;
import net.orekyuu.javatter.api.notification.NotificationService;
import net.orekyuu.javatter.api.util.lookup.Lookup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

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

    private ContextMenu createContextMenu(String url) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem save = new MenuItem("保存");
        save.setOnAction(e -> {
            HttpURLConnection connection = null;
            try {
                URL url1 = new URL(url);
                FileChooser chooser = new FileChooser();
                chooser.setTitle("ファイルを保存");
                String[] split = url1.getFile().split("/");
                chooser.setInitialFileName(split[split.length - 1]);
                File location = chooser.showSaveDialog(this);
                if (location == null) {
                    return;
                }
                connection = (HttpURLConnection) url1.openConnection();
                InputStream inputStream = connection.getInputStream();
                Files.copy(inputStream, location.toPath());
            } catch (IOException e1) {
                e1.printStackTrace();
                Lookup.lookup(NotificationService.class).showError("ファイルの保存に失敗しました", e1.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
        contextMenu.getItems().add(save);
        MenuItem close = new MenuItem("閉じる");
        close.setOnAction(e -> hide());
        contextMenu.getItems().add(close);

        return contextMenu;
    }

    public void showMovie(String url, Node anchor) {
        ContextMenu contextMenu = createContextMenu(url);

        Media media = new Media(url);
        this.mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(this.mediaPlayer);
        this.mediaPlayer.setOnEndOfMedia(() -> {
            this.mediaPlayer.seek(Duration.ZERO);
            this.mediaPlayer.play();
        });
        mediaView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(mediaView, event.getScreenX(), event.getScreenY());
            }
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
