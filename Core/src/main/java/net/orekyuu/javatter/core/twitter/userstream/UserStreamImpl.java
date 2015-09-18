package net.orekyuu.javatter.core.twitter.userstream;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.twitter.model.*;
import net.orekyuu.javatter.api.twitter.userstream.OnMention;
import net.orekyuu.javatter.api.twitter.userstream.UserStream;
import net.orekyuu.javatter.api.twitter.userstream.events.*;

public class UserStreamImpl implements UserStream {

    private MutableList<OnStatus> onStatusListeners = Lists.mutable.of();
    private MutableList<OnMention> onMentionListeners = Lists.mutable.of();
    private MutableList<OnException> onExceptionListeners = Lists.mutable.of();
    private MutableList<OnUserProfileUpdate> onUserProfileUpdateListeners = Lists.mutable.of();
    private MutableList<OnBlock> onBlockListeners = Lists.mutable.of();
    private MutableList<OnFavorite> onFavoriteListeners = Lists.mutable.of();
    private MutableList<OnFollow> onFollowListeners = Lists.mutable.of();
    private MutableList<OnUnblock> onUnblockListeners = Lists.mutable.of();
    private MutableList<OnUnfavorite> onUnfavoriteListeners = Lists.mutable.of();
    private MutableList<OnUnfollow> onUnfollowListeners = Lists.mutable.of();
    private MutableList<OnDirectMessage> onDirectMessageListeners = Lists.mutable.of();
    private MutableList<OnUserListUpdate> onUserListUpdateListeners = Lists.mutable.of();
    private MutableList<OnUserListSubscription> onUserListSubscriptionListeners = Lists.mutable.of();
    private MutableList<OnUserListUnsubscription> onUserListUnsubscriptionListeners = Lists.mutable.of();
    private MutableList<OnUserListMemberAddition> onUserListMemberAdditionListeners = Lists.mutable.of();
    private MutableList<OnUserListMemberDeletion> onUserListMemberDeletionListeners = Lists.mutable.of();
    private MutableList<OnUserListDeletion> onUserListDeletionListeners = Lists.mutable.of();
    private MutableList<OnUserListCreation> onUserListCreationListeners = Lists.mutable.of();
    private MutableList<OnStatusDeletion> onDeletionListeners = Lists.mutable.of();

    /**
     * 参照が切れたものをListから削除してリスナを登録する
     *
     * @param listener リスナ
     * @param list     リスト
     * @param <T>      リスナの型
     */
    private <T> void addListener(T listener, MutableList<T> list) {
        list.add(listener);
    }

    private <T> void removeListener(T listener, MutableList<T> list) {
        list.remove(listener);
    }

    @Override
    public UserStream onStatus(OnStatus onStatus) {
        addListener(onStatus, onStatusListeners);
        return this;
    }

    @Override
    public UserStream onMention(OnMention onMention) {
        addListener(onMention, onMentionListeners);
        return null;
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
        addListener(onDirectMessage, onDirectMessageListeners);
        return this;
    }

    @Override
    public UserStream onUserListUpdate(OnUserListUpdate onUserListUpdate) {
        addListener(onUserListUpdate, onUserListUpdateListeners);
        return this;
    }

    @Override
    public UserStream onUserListSubscription(OnUserListSubscription onUserListSubscription) {
        addListener(onUserListSubscription, onUserListSubscriptionListeners);
        return this;
    }

    @Override
    public UserStream onUserListUnsubscription(OnUserListUnsubscription onUserListUnsubscription) {
        addListener(onUserListUnsubscription, onUserListUnsubscriptionListeners);
        return this;
    }

    @Override
    public UserStream onUserListMemberAddition(OnUserListMemberAddition onUserListMemberAddition) {
        addListener(onUserListMemberAddition, onUserListMemberAdditionListeners);
        return this;
    }

    @Override
    public UserStream onUserListMemberDeletion(OnUserListMemberDeletion onUserListMemberDeletion) {
        addListener(onUserListMemberDeletion, onUserListMemberDeletionListeners);
        return this;
    }

    @Override
    public UserStream onUserListDeletion(OnUserListDeletion onUserListDeletion) {
        addListener(onUserListDeletion, onUserListDeletionListeners);
        return this;
    }

    @Override
    public UserStream onUserListCreation(OnUserListCreation onUserListCreation) {
        addListener(onUserListCreation, onUserListCreationListeners);
        return this;
    }

    @Override
    public UserStream removeOnStatus(OnStatus onStatus) {
        removeListener(onStatus, onStatusListeners);
        return this;
    }

    @Override
    public UserStream removeOnMention(OnMention onMention) {
        removeListener(onMention, onMentionListeners);
        return this;
    }

    @Override
    public UserStream removeOnException(OnException onException) {
        removeListener(onException, onExceptionListeners);
        return this;
    }

    @Override
    public UserStream removeOnUserProfileUpdate(OnUserProfileUpdate onUserProfileUpdate) {
        removeListener(onUserProfileUpdate, onUserProfileUpdateListeners);
        return this;
    }

    @Override
    public UserStream removeOnBlock(OnBlock onBlock) {
        removeListener(onBlock, onBlockListeners);
        return this;
    }

    @Override
    public UserStream removeOnFavorite(OnFavorite onFavorite) {
        removeListener(onFavorite, onFavoriteListeners);
        return this;
    }

    @Override
    public UserStream removeOnFollow(OnFollow onFollow) {
        removeListener(onFollow, onFollowListeners);
        return this;
    }

    @Override
    public UserStream removeOnUnblock(OnUnblock onUnblock) {
        removeListener(onUnblock, onUnblockListeners);
        return this;
    }

    @Override
    public UserStream removeOnUnfollow(OnUnfollow onUnfollow) {
        removeListener(onUnfollow, onUnfollowListeners);
        return this;
    }

    @Override
    public UserStream removeOnUnfavorite(OnUnfavorite onUnfavorite) {
        removeListener(onUnfavorite, onUnfavoriteListeners);
        return this;
    }

    @Override
    public UserStream removeOnDirectMessage(OnDirectMessage onDirectMessage) {
        removeListener(onDirectMessage, onDirectMessageListeners);
        return this;
    }

    @Override
    public UserStream removeOnUserListUpdate(OnUserListUpdate onUserListUpdate) {
        removeListener(onUserListUpdate, onUserListUpdateListeners);
        return this;
    }

    @Override
    public UserStream removeOnUserListSubscription(OnUserListSubscription onUserListSubscription) {
        removeListener(onUserListSubscription, onUserListSubscriptionListeners);
        return this;
    }

    @Override
    public UserStream removeOnUserListUnsubscription(OnUserListUnsubscription onUserListUnsubscription) {
        removeListener(onUserListUnsubscription, onUserListUnsubscriptionListeners);
        return this;
    }

    @Override
    public UserStream removeOnUserListMemberAddition(OnUserListMemberAddition onUserListMemberAddition) {
        removeListener(onUserListMemberAddition, onUserListMemberAdditionListeners);
        return this;
    }

    @Override
    public UserStream removeOnUserListMemberDeletion(OnUserListMemberDeletion onUserListMemberDeletion) {
        removeListener(onUserListMemberDeletion, onUserListMemberDeletionListeners);
        return this;
    }

    @Override
    public UserStream removeOnUserListDeletion(OnUserListDeletion onUserListDeletion) {
        removeListener(onUserListDeletion, onUserListDeletionListeners);
        return this;
    }

    @Override
    public UserStream removeOnUserListCreation(OnUserListCreation onUserListCreation) {
        removeListener(onUserListCreation, onUserListCreationListeners);
        return this;
    }

    //===========================================

    public void callStatus(Tweet tweet) {
        onStatusListeners.each(listener -> listener.onStatus(tweet));
    }

    public void callMentions(Tweet tweet) {
        onMentionListeners.each(listener -> listener.onMention(tweet, tweet.getOwner()));

    }

    public void callException(Exception e) {
        onExceptionListeners.each(listener -> listener.onException(e));

    }


    public void callUserProfileUpdate(User user) {
        onUserProfileUpdateListeners.each(listener -> listener.onUserProfileUpdate(user));

    }


    public void callBlock(User source, User blockedUser) {
        onBlockListeners.each(listener -> listener.onBlock(source, blockedUser));
    }


    public void callFavorite(User source, User target, Tweet tweet) {
        onFavoriteListeners.each(listener -> listener.onFavorite(source, target, tweet));
    }

    public void callFollow(User source, User followedUser) {
        onFollowListeners.each(listener -> listener.onFollow(source, followedUser));
    }

    public void callUnblock(User source, User unblockedUser) {
        onUnblockListeners.each(listener -> listener.onUnblock(source, unblockedUser));
    }

    public void callUnfollow(User source, User unfollowedUser) {
        onUnfollowListeners.each(listener -> listener.onUnfollow(source, unfollowedUser));
    }

    public void callUnfavorite(User source, User target, Tweet unfavoritedTweet) {
        onUnfavoriteListeners.each(listener -> listener.onUnfavorite(source, target, unfavoritedTweet));
    }

    public void callScrubGeo(long userId, long upToStatusId) {
    }

    public void callDirectMessage(DirectMessage directMessage) {
        onDirectMessageListeners.each(listener -> listener.onDirectMessage(directMessage));
    }

    public void callDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        onDeletionListeners.each(listener -> listener.onDelete(statusDeletionNotice));
    }

    public void callUserListUpdate(User user, UserList userList) {
        onUserListUpdateListeners.each(listener -> listener.onUserListUpdate(user, userList));
    }

    public void callUserListSubscription(User user, User user1, UserList userList) {
        onUserListSubscriptionListeners.each(listener -> listener.onUserListSubscription(user, user1, userList));
    }

    public void callUserListUnsubscription(User user, User user1, UserList userList) {
        onUserListUnsubscriptionListeners.each(listener -> listener.onUserListUnsubscription(user, user1, userList));
    }

    public void callUserListMemberAddition(User user, User user1, UserList userList) {
        onUserListMemberAdditionListeners.each(listener -> listener.onUserListMemberAddition(user, user1, userList));
    }

    public void callUserListMemberDeletion(User user, User user1, UserList userList) {
        onUserListMemberDeletionListeners.each(listener -> listener.onUserListMemberDeletion(user, user1, userList));
    }

    public void callUserListDeletion(User user, UserList userList) {
        onUserListDeletionListeners.each(listener -> listener.onUserListDeletion(user, userList));
    }

    public void callUserListCreation(User user, UserList userList) {
        onUserListCreationListeners.each(listener -> listener.onUserListCreation(user, userList));
    }
}
