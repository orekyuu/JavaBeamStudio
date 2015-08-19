package net.orekyuu.javatter.api.userwindow;

import javafx.beans.property.StringProperty;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.api.twitter.model.User;

/**
 * ユーザーウィンドウのタブ
 */
public interface UserWindowTab {

    /**
     * 表示する内容が変更される時に呼び出されます。
     * @since 1.0.0
     * @param user 表示するユーザー
     * @param owner 持ち主
     */
    void update(User user, TwitterUser owner);

    /**
     * タイトルのプロパティを返します。
     * 返したプロパティはタイトルとバインドされます。
     * @since 1.0.0
     * @return タイトルのプロパティ
     */
    StringProperty titleProperty();
}
