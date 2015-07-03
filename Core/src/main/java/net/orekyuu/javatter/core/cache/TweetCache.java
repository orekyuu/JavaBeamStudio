package net.orekyuu.javatter.core.cache;

import com.gs.collections.api.map.primitive.MutableLongObjectMap;
import com.gs.collections.impl.map.mutable.primitive.MutableLongObjectMapFactoryImpl;
import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.util.Optional;

/**
 * Twitterインスタンスのキャッシュ
 */
public class TweetCache {

    private static final TweetCache instance = new TweetCache();

    //Thread Safe
    private final MutableLongObjectMap<Tweet> cache;


    public TweetCache() {
        //なんか間違ってる気がするぞ・・・たぶんどこかにstatic factoryがあるはず
        MutableLongObjectMap<Tweet> map = new MutableLongObjectMapFactoryImpl().empty();
        cache = map.asSynchronized();
    }

    public static TweetCache getInstance() {
        return instance;
    }

    public void update(Tweet tweet) {
        cache.put(tweet.getStatusId(), tweet);
    }

    public Optional<Tweet> getById(long id) {
        return Optional.ofNullable(cache.get(id));
    }
}
