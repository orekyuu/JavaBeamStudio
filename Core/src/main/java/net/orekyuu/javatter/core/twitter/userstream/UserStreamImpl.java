package net.orekyuu.javatter.core.twitter.userstream;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.twitter.model.Tweet;
import net.orekyuu.javatter.api.twitter.userstream.UserStream;
import net.orekyuu.javatter.api.twitter.userstream.events.*;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.function.Predicate;

public class UserStreamImpl implements UserStream {

    private MutableList<WeakReference<OnStatus>> onStatusListeners = Lists.mutable.of();
    private MutableList<WeakReference<OnException>> onExceptionListeners = Lists.mutable.of();
    private MutableList<WeakReference<OnUserProfileUpdate>> onUserProfileUpdateListeners = Lists.mutable.of();
    private MutableList<WeakReference<OnBlock>> onBlockListeners = Lists.mutable.of();
    private MutableList<WeakReference<OnFavorite>> onFavoriteListeners = Lists.mutable.of();
    private MutableList<WeakReference<OnFollow>> onFollowListeners = Lists.mutable.of();
    private MutableList<WeakReference<OnUnblock>> onUnblockListeners = Lists.mutable.of();
    private MutableList<WeakReference<OnUnfavorite>> onUnfavoriteListeners = Lists.mutable.of();
    private MutableList<WeakReference<OnUnfollow>> onUnfollowListeners = Lists.mutable.of();

    /**
     * 参照が切れたものをListから削除してリスナを登録する
     *
     * @param listener リスナ
     * @param list     リスト
     * @param <T>      リスナの型
     */
    private <T> void addListener(T listener, MutableList<WeakReference<T>> list) {
        list.removeIf((Predicate<WeakReference<T>>) ref -> ref.get() == null);
        list.add(new WeakReference<>(listener));
    }

    @Override
    public UserStream onStatus(OnStatus onStatus) {
        addListener(onStatus, onStatusListeners);
        return this;
    }

    @Override
    public UserStream onException(OnException onException) {
        addListener(onException, onExceptionListeners);
        return this;
    }

    @Override
    public UserStream onUserProfileUpdate(OnUserProfileUpdate onUserProfileUpdate) {
        addListener(onUserProfileUpdate, onUserProfileUpdateListeners);
        return this;
    }

    @Override
    public UserStream onBlock(OnBlock onBlock) {
        addListener(onBlock, onBlockListeners);
        return this;
    }

    @Override
    public UserStream onFavorite(OnFavorite onFavorite) {
        addListener(onFavorite, onFavoriteListeners);
        return this;
    }

    @Override
    public UserStream onFollow(OnFollow onFollow) {
        addListener(onFollow, onFollowListeners);
        return this;
    }

    @Override
    public UserStream onUnblock(OnUnblock onUnblock) {
        addListener(onUnblock, onUnblockListeners);
        return this;
    }

    @Override
    public UserStream onUnfollow(OnUnfollow onUnfollow) {
        addListener(onUnfollow, onUnfollowListeners);
        return this;
    }

    @Override
    public UserStream onUnfavorite(OnUnfavorite onUnfavorite) {
        addListener(onUnfavorite, onUnfavoriteListeners);
        return this;
    }

    @Override
    public UserStream onDirectMessage(OnDirectMessage onDirectMessage) {
        //TODO 後で実装
        return this;
    }

    @Override
    public UserStream onUserListUpdate(OnUserListUpdate onUserListUpdate) {
        //TODO 後で実装
        return this;
    }

    @Override
    public UserStream onUserListSubscription(OnUserListSubscription onUserListSubscription) {
        //TODO 後で実装
        return this;
    }

    @Override
    public UserStream onUserListUnsubscription(OnUserListUnsubscription onUserListUnsubscription) {
        //TODO 後で実装
        return this;
    }

    @Override
    public UserStream onUserListMemberAddition(OnUserListMemberAddition onUserListMemberAddition) {
        //TODO 後で実装
        return this;
    }

    @Override
    public UserStream onUserListMemberDeletion(OnUserListMemberDeletion onUserListMemberDeletion) {
        //TODO 後で実装
        return this;
    }

    @Override
    public UserStream onUserListDeletion(OnUserListDeletion onUserListDeletion) {
        //TODO 後で実装
        return this;
    }

    @Override
    public UserStream onUserListCreation(OnUserListCreation onUserListCreation) {
        //TODO 後で実装
        return this;
    }

    //===========================================

    public void callStatus(Tweet tweet) {
        onStatusListeners.collect(Reference::get)
                .select(listener -> listener != null)
                .each(listener -> listener.onStatus(tweet));

    }


    public void callException(Exception e) {
        onExceptionListeners.collect(Reference::get)
                .select(listener -> listener != null)
                .each(listener -> listener.onException(e));

    }


    public void callUserProfileUpdate(OnUserProfileUpdate onUserProfileUpdate) {
        addListener(onUserProfileUpdate, onUserProfileUpdateListeners);

    }


    public void callBlock(OnBlock onBlock) {
        addListener(onBlock, onBlockListeners);

    }


    public void callFavorite(OnFavorite onFavorite) {
        addListener(onFavorite, onFavoriteListeners);

    }


    public void callFollow(OnFollow onFollow) {
        addListener(onFollow, onFollowListeners);

    }


    public void callUnblock(OnUnblock onUnblock) {
        addListener(onUnblock, onUnblockListeners);

    }


    public void callUnfollow(OnUnfollow onUnfollow) {
        addListener(onUnfollow, onUnfollowListeners);

    }


    public void callUnfavorite(OnUnfavorite onUnfavorite) {
        addListener(onUnfavorite, onUnfavoriteListeners);

    }


    public void callDirectMessage(OnDirectMessage onDirectMessage) {
        //TODO 後で実装

    }


    public void callUserListUpdate(OnUserListUpdate onUserListUpdate) {
        //TODO 後で実装

    }


    public void callUserListSubscription(OnUserListSubscription onUserListSubscription) {
        //TODO 後で実装

    }


    public void callUserListUnsubscription(OnUserListUnsubscription onUserListUnsubscription) {
        //TODO 後で実装

    }


    public void callUserListMemberAddition(OnUserListMemberAddition onUserListMemberAddition) {
        //TODO 後で実装

    }


    public void callUserListMemberDeletion(OnUserListMemberDeletion onUserListMemberDeletion) {
        //TODO 後で実装

    }


    public void callUserListDeletion(OnUserListDeletion onUserListDeletion) {
        //TODO 後で実装

    }


    public void callUserListCreation(OnUserListCreation onUserListCreation) {
        //TODO 後で実装

    }
}
