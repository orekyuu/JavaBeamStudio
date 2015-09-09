package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.Tweet;

/**
 * お気に入りに関する操作
 * @since 1.0.0
 */
public interface FavoriteControl {
    /**
     * 指定されたツイートをお気に入りに追加します
     * @since 1.0.0
     * @param tweet お気に入りに追加するツイート
     *
     */
    void favorite(Tweet tweet);

    /**
     * 指定されたツイートを非同期でお気に入りに追加します
     * @since 1.0.0
     * @param tweet お気に入りに追加するツイート
     */
    void favoriteAsync(Tweet tweet);

    /**
     * 指定されたツイートをお気に入りから削除します
     * @since 1.0.0
     * @param tweet お気に入りから削除します
     */
    void unFavorite(Tweet tweet);

    /**
     * 指定されたツイートを非同期でお気に入りから削除します
     * @since 1.0.0
     * @param tweet お気に入りから削除します
     */
    void unFavoriteAsync(Tweet tweet);
}
