package net.orekyuu.javatter.core.service;

import com.gs.collections.api.map.MutableMap;
import com.gs.collections.impl.factory.Maps;
import javafx.scene.image.Image;
import net.orekyuu.javatter.api.service.UserIconStorage;
import net.orekyuu.javatter.api.twitter.model.User;

import java.lang.ref.SoftReference;

public class UserIconStorageImpl implements UserIconStorage {

    private MutableMap<String, SoftReference<Image>> icons = Maps.mutable.empty();

    @Override
    public Image find(User user) {
        SoftReference<Image> reference = icons.get(user.getScreenName());
        if (reference == null) {
            Image image = createImage(user);
            SoftReference<Image> value = new SoftReference<>(image);
            icons.put(user.getScreenName(), value);
            return image;
        }

        Image image = reference.get();
        if (image == null) {
            image = createImage(user);
            SoftReference<Image> value = new SoftReference<>(image);
            icons.put(user.getScreenName(), value);
        }
        return image;
    }

    private Image createImage(User user) {
        System.out.println("create: " + user.getScreenName() + ", " + this);
        return new Image(user.getProfileImageURL(), true);
    }
}
