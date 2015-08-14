package net.orekyuu.javatter.api.service;

import net.orekyuu.javatter.api.twitter.model.User;

/**
 * ユーザー情報ウィンドウを表示するためのサービス
 */
@Service
public interface UserWindowService {

    /**
     * 指定されたユーザーの情報ウィンドウを開きます。
     * @since 1.0.0
     * @param user ユーザー
     */
    void open(User user);
}
