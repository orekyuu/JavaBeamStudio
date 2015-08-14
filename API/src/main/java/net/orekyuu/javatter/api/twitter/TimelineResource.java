package net.orekyuu.javatter.api.twitter;

import com.gs.collections.api.list.MutableList;
import net.orekyuu.javatter.api.twitter.model.Tweet;

/**
 * タイムラインを取得する操作
 */
public interface TimelineResource {

    /**
     * タイムラインを公式APIから取得します。
     * @return 取得したツイート
     */
    MutableList<Tweet> getHomeTimeline();
}
