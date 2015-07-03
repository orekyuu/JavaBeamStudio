package net.orekyuu.javatter.api.service;

import javafx.scene.image.Image;
import net.orekyuu.javatter.api.twitter.model.User;

public interface UserIconStorage {

    Image find(User user);
}
