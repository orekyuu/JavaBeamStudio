package net.orekyuu.javatter.api.twitter;

import com.gs.collections.api.list.MutableList;
import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.util.concurrent.CompletableFuture;

/**
 * タイムラインを取得する操作
 * @since 1.0.0
 */
public interface TimelineResource {

    /**
     * タイムラインを公式APIから取得します。
     * @deprecated {@link #fetchHomeTimeline()}
     * @since 1.0.0
     * @return 取得したツイート
     */
    @Deprecated
    MutableList<Tweet> getHomeTimeline();

    /**
     * タイムラインを公式APIから取得します。
     * @since 1.1.0
     * @return 取得したツイート
     */
    CompletableFuture<MutableList<Tweet>> fetchHomeTimeline();
}
