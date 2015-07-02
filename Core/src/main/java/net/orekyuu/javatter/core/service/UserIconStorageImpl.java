package net.orekyuu.javatter.core.service;

import com.gs.collections.api.map.MutableMap;
import com.gs.collections.impl.factory.Maps;
import javafx.scene.image.Image;
import net.orekyuu.javatter.api.service.UserIconStorage;
import net.orekyuu.javatter.api.twitter.model.User;

public class UserIconStorageImpl implements UserIconStorage {

    private MutableMap<String, Image> icons = Maps.mutable.empty();

    @Override
    public Image find(User user) {
        return icons.getIfAbsent(user.getProfileImageURL(), () -> new Image(user.getProfileImageURL(), true));
    }
}
