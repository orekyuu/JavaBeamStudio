package net.orekyuu.javatter.api.userwindow;

import net.orekyuu.javatter.api.service.Service;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.User;

/**
 * ユーザー情報ウィンドウを表示するためのサービス
 * @since 1.0.0
 */
@Service
public interface UserWindowService {

    /**
     * 指定されたユーザーの情報ウィンドウを開きます。
     * @since 1.0.0
     * @param user ユーザー
     * @param owner Owner
     */
    void open(User user, TwitterUser owner);
}
