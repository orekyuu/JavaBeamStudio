package net.orekyuu.javatter.api.action;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.service.Service;

/**
 * アクションの管理を行うサービスです。
 * @since 1.0.0
 */
@Service
public interface ActionManager {

    /**
     * メニューバーにアクションを登録します。
     * @since 1.0.0
     * @throws ActionDuplicateException アクションIDが重複していた時
     * @param action 登録するアクション
     */
    void registerMenuAction(MenuAction action);

    /**
     * ツイートセルへアクションを登録します。
     * @throws ActionDuplicateException アクションIDが重複していた時
     * @since 1.0.0
     * @param action 登録するアクション
     */
    void registerTweetCellAction(TweetCellAction action);

    /**
     * @since 1.0.0
     * @return 登録されているMenuActionのリスト
     */
    ImmutableList<MenuAction> getMenuActions();

    /**
     * @since 1.0.0
     * @return 登録されているTweetCellActionのリスト
     */
    ImmutableList<TweetCellAction> getTweetCellActions();
}
