package net.orekyuu.javatter.core.cache;

import com.gs.collections.api.map.primitive.MutableLongObjectMap;
import com.gs.collections.impl.map.mutable.primitive.MutableLongObjectMapFactoryImpl;
import net.orekyuu.javatter.api.twitter.model.User;

import java.util.Optional;

/**
 * ユーザーインスタンスのキャッシュ
 */
public class UserCache {

    private static final UserCache instance = new UserCache();

    private final MutableLongObjectMap<User> cache;

    public UserCache() {
        //なんか間違ってる気がするぞ・・・たぶんどこかにstatic factoryがあるはず
        MutableLongObjectMap<User> map = new MutableLongObjectMapFactoryImpl().empty();
        cache = map.asSynchronized();
    }

    public static UserCache getInstance() {
        return instance;
    }

    public void update(User user) {
        cache.put(user.getId(), user);
    }

    public Optional<User> getById(long id) {
        return Optional.ofNullable(cache.get(id));
    }

    public Optional<User> getByName(String name) {
        return cache.values().stream().filter(user -> user.getScreenName().equals(name)).findFirst();
    }
}
