package net.orekyuu.javatter.api.service;

import javafx.scene.image.Image;
import net.orekyuu.javatter.api.twitter.model.User;

/**
 * キャッシュされたユーザーアイコンへアクセスするサービス
 */
@Service
public interface UserIconStorage {

    /**
     * アイコンを検索します。
     * @param user ユーザー
     * @return Image
     * @since 1.0.0
     */
    Image find(User user);
}
