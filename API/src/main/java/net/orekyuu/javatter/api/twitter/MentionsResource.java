package net.orekyuu.javatter.api.twitter;

import com.gs.collections.api.list.MutableList;
import net.orekyuu.javatter.api.twitter.model.Tweet;

/**
 * メンションを取得する操作
 * @since 1.0.0
 */
public interface MentionsResource {

    /**
     * メンションを公式APIから取得します
     * @since 1.0.0
     * @return 取得したツイート
     */
    MutableList<Tweet> getMentions();
}
