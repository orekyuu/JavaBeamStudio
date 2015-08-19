package net.orekyuu.javatter.core.controller;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.factory.Lists;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.userwindow.UserWindowTab;
import net.orekyuu.javatter.api.userwindow.UserWindowTabManager;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserWindowController implements Initializable {
    public ImageView userImage;
    public AnchorPane header;
    public TabPane tabPane;

    private UserInfoController controller;

    @Inject
    private UserWindowTabManager manager;

    private MutableList<UserWindowTab> tabControllers = Lists.mutable.of();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager.registeredTabFxmlURLs()
                .collect(JavatterFXMLLoader::new)
                .each(loader -> {
                    try {
                        Parent p = loader.load();
                        UserWindowTab userTab = loader.getController();
                        StringProperty titleProperty = userTab.titleProperty();
                        Tab tab = new Tab(titleProperty.get(), p);
                        tab.textProperty().bind(titleProperty);
                        tabPane.getTabs().add(tab);
                        tabControllers.add(userTab);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void setModel(User user, TwitterUser owner) {
        String imageURL = user.getProfileBackgroundImageURL();
        header.setStyle(String.format("-fx-background-image: url(\"%s\");-fx-background-repeat: repeat;-fx-background-position: center center;", imageURL));

        userImage.setImage(new Image(user.getOriginalProfileImageURL()));

        tabControllers.each(c -> c.update(user, owner));
    }


}
