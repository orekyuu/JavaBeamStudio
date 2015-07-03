package net.orekyuu.javatter.core.control;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.io.IOException;

public class TweetCell extends ListCell<Tweet> {

    private GridPane parent;
    private TweetCellController controller;
    private ObjectProperty<Tweet> tweet = new SimpleObjectProperty<>();

    public TweetCell() throws IOException {
        JavatterFXMLLoader loader = new JavatterFXMLLoader(getClass().getResource("/layout/tweetCell.fxml"));
        parent = loader.load();
        controller = loader.getController();
        controller.tweetProperty().bind(tweet);
    }

    public ObjectProperty<TwitterUser> ownerProperty() {
        return controller.ownerProperty();
    }

    @Override
    protected void updateItem(Tweet item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || tweet == null) {
            setGraphic(null);
        } else {
            tweet.setValue(item);
            if (getGraphic() == null) {
                setGraphic(parent);
                DoubleBinding binding = Bindings.add(-20.0, getListView().widthProperty());
                parent.prefWidthProperty().bind(binding);
                parent.maxWidthProperty().bind(binding);
                parent.prefWidth(getWidth());
                parent.maxWidth(getWidth());
            }
        }
    }
}
