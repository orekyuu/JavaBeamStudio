package net.orekyuu.javatter.api.twitter;

import com.gs.collections.api.list.MutableList;
import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.util.concurrent.CompletableFuture;

/**
 * メンションを取得する操作
 * @since 1.0.0
 */
public interface MentionsResource {

    /**
     * メンションを公式APIから取得します
     * @deprecated {@link #fetchMentions()}
     * @since 1.0.0
     * @return 取得したツイート
     */
    @Deprecated
    MutableList<Tweet> getMentions();

    /**
     * メンションを公式APIから取得します
     * @since 1.1.0
     * @return 取得したツイート
     */
    CompletableFuture<MutableList<Tweet>> fetchMentions();
}
