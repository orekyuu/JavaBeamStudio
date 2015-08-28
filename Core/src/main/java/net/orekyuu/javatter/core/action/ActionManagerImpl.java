package net.orekyuu.javatter.core.action;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.action.ActionDuplicateException;
import net.orekyuu.javatter.api.action.ActionManager;
import net.orekyuu.javatter.api.action.MenuAction;
import net.orekyuu.javatter.api.action.TweetCellAction;

public class ActionManagerImpl implements ActionManager {

    private MutableList<MenuAction> menuActions = Lists.mutable.empty();
    private MutableList<TweetCellAction> tweetCellActions = Lists.mutable.empty();
    @Override
    public void registerMenuAction(MenuAction action) {
        boolean anySatisfy = menuActions.anySatisfy(e -> e.getActionId().equals(action.getActionId()));
        if (anySatisfy) {
            throw new ActionDuplicateException(action.getActionId());
        }
        menuActions.add(action);
    }

    @Override
    public void registerTweetCellAction(TweetCellAction action) {
        boolean anySatisfy = tweetCellActions.anySatisfy(e -> e.getActionId().equals(action.getActionId()));
        if (anySatisfy) {
            throw new ActionDuplicateException(action.getActionId());
        }
        tweetCellActions.add(action);
    }

    @Override
    public ImmutableList<MenuAction> getMenuActions() {
        return menuActions.toImmutable();
    }

    @Override
    public ImmutableList<TweetCellAction> getTweetCellActions() {
        return tweetCellActions.toImmutable();
    }
}
