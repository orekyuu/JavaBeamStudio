package net.orekyuu.javatter.api.twitter.control;

import net.orekyuu.javatter.api.twitter.model.Tweet;

/**
 * お気に入りに関する操作を行うことができる
 *
 * @since 1.0.0
 */
public interface FavoriteControl {
    /**
     * お気に入りに登録します。<br>
     * 非同期で行う場合は{@link FavoriteControl#favoriteAsync(Tweet)}を使用してください。
     *
     * @param tweet 対象のツイート
     * @since 1.0.0
     */
    void favorite(Tweet tweet);

    /**
     * 非同期でお気に入りに登録します。<br>
     *
     * @param tweet 対象のツイート
     * @since 1.0.0
     */
    void favoriteAsync(Tweet tweet);

    /**
     * お気に入りを解除します。<br>
     * 非同期で行う場合は{@link FavoriteControl#unFavoriteAsync(Tweet)}を使用してください。
     *
     * @param tweet 対象のツイート
     * @since 1.0.0
     */
    void unFavorite(Tweet tweet);

    /**
     * 非同期でお気に入りを解除します。<br>
     *
     * @param tweet 対象のツイート
     * @since 1.0.0
     */
    void unFavoriteAsync(Tweet tweet);
}
