package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.twitter.model.User;

/**
 * Userのリソースにアクセスする操作
 */
public interface UserResource {

    /**
     * 公式APIからスクリーンネームを使用してユーザー情報を取得します
     * @since 1.0.0
     * @param screenName スクリーンネーム
     * @return 取得したユーザー
     */
    User findUser(String screenName);

    /**
     * 公式APIからIDを使用してユーザー情報を取得します
     * @since 1.0.0
     * @param id id
     * @return 取得したユーザー
     */
    User findUser(long id);
}
