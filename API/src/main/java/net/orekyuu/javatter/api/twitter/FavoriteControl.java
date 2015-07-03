package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.Tweet;

public interface FavoriteControl {
    void favorite(Tweet tweet);

    void favoriteAsync(Tweet tweet);

    void unFavorite(Tweet tweet);

    void unFavoriteAsync(Tweet tweet);
}
