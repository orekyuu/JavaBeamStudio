package net.orekyuu.javatter.core.cache;

import com.gs.collections.api.map.primitive.MutableLongBooleanMap;
import com.gs.collections.api.map.primitive.MutableLongObjectMap;
import com.gs.collections.impl.map.mutable.primitive.MutableLongBooleanMapFactoryImpl;
import com.gs.collections.impl.map.mutable.primitive.MutableLongObjectMapFactoryImpl;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.Tweet;

public class RetweetCache {

    private static final RetweetCache instance = new RetweetCache();

    private final MutableLongObjectMap<MutableLongBooleanMap> cache;

    public RetweetCache() {
        MutableLongObjectMap<MutableLongBooleanMap> map = new MutableLongObjectMapFactoryImpl().empty();
        cache = map.asSynchronized();
    }

    public static RetweetCache getInstance() {
        return instance;
    }

    /**
     * リツイートの状態を更新する
     *
     * @param account   アカウント
     * @param tweet     ツイート
     * @param retweeted お気に入りの状態
     */
    public void update(TwitterUser account, Tweet tweet, boolean retweeted) {
        //Mapがなければ用意する
        MutableLongBooleanMap favoritedCache = cache
                .getIfAbsentPut(account.getUser().getId(), new MutableLongBooleanMapFactoryImpl().empty().asSynchronized());
        favoritedCache.put(tweet.getStatusId(), retweeted);
    }

    public boolean isRetweeted(TwitterUser account, Tweet tweet) {
        //Mapがなければ用意する
        MutableLongBooleanMap retweetedCache = cache
                .getIfAbsentPut(account.getUser().getId(), new MutableLongBooleanMapFactoryImpl().empty().asSynchronized());
        return retweetedCache.get(tweet.getStatusId());
    }

    public void clear() {
        cache.clear();
    }

    public void clearAccountCache(TwitterUser account) {
        cache.remove(account.getUser().getId());
    }
}
