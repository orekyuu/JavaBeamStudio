package net.orekyuu.javatter.core.cache;

import com.gs.collections.api.map.primitive.MutableLongObjectMap;
import com.gs.collections.impl.map.mutable.primitive.MutableLongObjectMapFactoryImpl;
import net.orekyuu.javatter.api.twitter.model.User;

import java.lang.ref.SoftReference;
import java.util.Optional;

/**
 * ユーザーインスタンスのキャッシュ
 */
public class UserCache {

    private static final UserCache instance = new UserCache();

    private final MutableLongObjectMap<SoftReference<User>> cache;

    public UserCache() {
        //なんか間違ってる気がするぞ・・・たぶんどこかにstatic factoryがあるはず
        MutableLongObjectMap<SoftReference<User>> map = new MutableLongObjectMapFactoryImpl().empty();
        cache = map.asSynchronized();
    }

    public static UserCache getInstance() {
        return instance;
    }

    public void update(User user) {
        cache.put(user.getId(), new SoftReference<>(user));
    }

    public Optional<User> getById(long id) {
        SoftReference<User> reference = cache.get(id);
        if (reference == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(reference.get());
    }

    public Optional<User> getByName(String name) {
        return cache.values().stream()
                .map(SoftReference::get)
                .filter(user -> user != null && user.getScreenName().equals(name))
                .findAny();
    }
}
