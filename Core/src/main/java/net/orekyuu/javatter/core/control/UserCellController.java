package net.orekyuu.javatter.core.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import net.orekyuu.javatter.api.service.UserIconStorage;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.userwindow.UserWindowService;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class UserCellController implements Initializable {

    public ImageView profileImage;
    public Label name;
    public Label description;
    private ObjectProperty<TwitterUser> owner = new SimpleObjectProperty<>();
    private ObjectProperty<User> user = new SimpleObjectProperty<>();

    @Inject
    private UserWindowService userWindowService;
    @Inject
    private UserIconStorage iconStorage;

    public ObjectProperty<TwitterUser> ownerProperty() {
        return owner;
    }

    public ObjectProperty<User> userProperty() {
        return user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user.addListener((observable, oldValue, newValue) -> {
            profileImage.setImage(iconStorage.find(newValue));
            name.setText(newValue.getName());
            description.setText(newValue.getDescription());
        });

    }

    public void openUserProfile() {
        TwitterUser owner = this.owner.getValue();
        User user = this.user.getValue();
        if (user != null && owner != null) {
            userWindowService.open(user, owner);
        }
    }
}
