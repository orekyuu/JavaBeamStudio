package net.orekyuu.javatter.core.control;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.model.User;

import java.io.IOException;

public class UserCell extends ListCell<User> {

    private VBox parent;
    private UserCellController controller;
    private ObjectProperty<User> user = new SimpleObjectProperty<>();

    public UserCell() throws IOException {
        JavatterFXMLLoader loader = new JavatterFXMLLoader(getClass().getResource("/layout/userCell.fxml"));
        parent = loader.load();
        controller = loader.getController();
        controller.userProperty().bind(user);
    }

    public ObjectProperty<TwitterUser> ownerProperty() {
        return controller.ownerProperty();
    }

    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || user == null) {
            setGraphic(null);
        } else {
            user.setValue(item);
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
