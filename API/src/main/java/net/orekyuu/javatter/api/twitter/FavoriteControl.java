package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.Tweet;

import java.util.concurrent.CompletableFuture;

/**
 * お気に入りに関する操作
 * @since 1.0.0
 */
public interface FavoriteControl {

    /**
     * 指定されたツイートをお気に入りに追加します
     * @deprecated {@link #tryFavorite(Tweet)}
     * @since 1.0.0
     * @param tweet お気に入りに追加するツイート
     */
    @Deprecated
    void favorite(Tweet tweet);

    /**
     * 指定されたツイートを非同期でお気に入りに追加します
     * @deprecated {@link #tryFavorite(Tweet)}
     * @since 1.0.0
     * @param tweet お気に入りに追加するツイート
     */
    @Deprecated
    void favoriteAsync(Tweet tweet);

    /**
     * 指定されたツイートをお気に入りから削除します
     * @deprecated {@link #tryUnfavorite(Tweet)}
     * @since 1.0.0
     * @param tweet お気に入りから削除するツイート
     */
    @Deprecated
    void unFavorite(Tweet tweet);

    /**
     * 指定されたツイートを非同期でお気に入りから削除します
     * @deprecated {@link #tryUnfavorite(Tweet)}
     * @since 1.0.0
     * @param tweet お気に入りから削除するツイート
     */
    @Deprecated
    void unFavoriteAsync(Tweet tweet);

    /**
     * 指定されたツイートをお気に入りに追加します
     * @since 1.1.0
     * @param tweet お気に入りに追加するツイート
     * @return お気に入りの結果
     */
    CompletableFuture<Tweet> tryFavorite(Tweet tweet);

    /**
     * 指定されたツイートをお気に入りから削除します
     * @since 1.1.0
     * @param tweet お気に入りから削除するツイート
     * @return お気に入りから削除した結果
     */
    CompletableFuture<Tweet> tryUnfavorite(Tweet tweet);
}
