package net.orekyuu.javatter.api.twitter.userstream;

import net.orekyuu.javatter.api.twitter.userstream.events.*;

/**
 * @since 1.0.0
 */
public interface UserStream {

    /**
     * @since 1.0.0
     * @param onStatus onStatus
     * @return メソッドチェーンのためのthis
     */
    UserStream onStatus(OnStatus onStatus);

    /**
     * @since 1.0.0
     * @param onMention onMention
     * @return メソッドチェーンのためのthis
     */
    UserStream onMention(OnMention onMention);

    /**
     * @since 1.0.0
     * @param onException onException
     * @return メソッドチェーンのためのthis
     */
    UserStream onException(OnException onException);

    /**
     * @since 1.0.0
     * @param onUserProfileUpdate onUserProfileUpdate
     * @return メソッドチェーンのためのthis
     */
    UserStream onUserProfileUpdate(OnUserProfileUpdate onUserProfileUpdate);

    /**
     * @since 1.0.0
     * @param onBlock onBlock
     * @return メソッドチェーンのためのthis
     */
    UserStream onBlock(OnBlock onBlock);

    /**
     * @since 1.0.0
     * @param onFavorite onFavorite
     * @return メソッドチェーンのためのthis
     */
    UserStream onFavorite(OnFavorite onFavorite);

    /**
     * @since 1.0.0
     * @param onFollow onFollow
     * @return メソッドチェーンのためのthis
     */
    UserStream onFollow(OnFollow onFollow);

    /**
     * @since 1.0.0
     * @param onUnblock onUnblock
     * @return メソッドチェーンのためのthis
     */
    UserStream onUnblock(OnUnblock onUnblock);

    /**
     * @since 1.0.0
     * @param onUnfollow onUnfollow
     * @return メソッドチェーンのためのthis
     */
    UserStream onUnfollow(OnUnfollow onUnfollow);

    /**
     * @since 1.0.0
     * @param onUnfavorite onUnfavorite
     * @return メソッドチェーンのためのthis
     */
    UserStream onUnfavorite(OnUnfavorite onUnfavorite);

    /**
     * @since 1.0.0
     * @param onDirectMessage onDirectMessage
     * @return メソッドチェーンのためのthis
     */
    UserStream onDirectMessage(OnDirectMessage onDirectMessage);

    /**
     * @since 1.0.0
     * @param onUserListUpdate onUserListUpdate
     * @return メソッドチェーンのためのthis
     */
    UserStream onUserListUpdate(OnUserListUpdate onUserListUpdate);

    /**
     * @since 1.0.0
     * @param onUserListSubscription onUserListSubscription
     * @return メソッドチェーンのためのthis
     */
    UserStream onUserListSubscription(OnUserListSubscription onUserListSubscription);

    /**
     *  @since 1.0.0
     * @param onUserListUnsubscription onUserListUnsubscription
     * @return メソッドチェーンのためのthis
     */
    UserStream onUserListUnsubscription(OnUserListUnsubscription onUserListUnsubscription);

    /**
     * @since 1.0.0
     * @param onUserListMemberAddition onUserListMemberAddition
     * @return メソッドチェーンのためのthis
     */
    UserStream onUserListMemberAddition(OnUserListMemberAddition onUserListMemberAddition);

    /**
     * @since 1.0.0
     * @param onUserListMemberDeletion onUserListMemberDeletion
     * @return メソッドチェーンのためのthis
     */
    UserStream onUserListMemberDeletion(OnUserListMemberDeletion onUserListMemberDeletion);

    /**
     * @since 1.0.0
     * @param onUserListDeletion onUserListDeletion
     * @return メソッドチェーンのためのthis
     */
    UserStream onUserListDeletion(OnUserListDeletion onUserListDeletion);

    /**
     * @since 1.0.0
     * @param onUserListCreation onUserListCreation
     * @return メソッドチェーンのためのthis
     */
    UserStream onUserListCreation(OnUserListCreation onUserListCreation);

    /**
     * @since 1.0.1
     * @param onStatus onStatus
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnStatus(OnStatus onStatus);

    /**
     * @since 1.0.1
     * @param onMention onMention
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnMention(OnMention onMention);

    /**
     * @since 1.0.1
     * @param onException onException
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnException(OnException onException);

    /**
     * @since 1.0.1
     * @param onUserProfileUpdate onUserProfileUpdate
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnUserProfileUpdate(OnUserProfileUpdate onUserProfileUpdate);

    /**
     * @since 1.0.1
     * @param onBlock onBlock
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnBlock(OnBlock onBlock);

    /**
     * @since 1.0.1
     * @param onFavorite onFavorite
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnFavorite(OnFavorite onFavorite);

    /**
     * @since 1.0.1
     * @param onFollow onFollow
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnFollow(OnFollow onFollow);

    /**
     * @since 1.0.1
     * @param onUnblock onUnblock
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnUnblock(OnUnblock onUnblock);

    /**
     * @since 1.0.1
     * @param onUnfollow onUnfollow
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnUnfollow(OnUnfollow onUnfollow);

    /**
     * @since 1.0.1
     * @param onUnfavorite onUnfavorite
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnUnfavorite(OnUnfavorite onUnfavorite);

    /**
     * @since 1.0.1
     * @param onDirectMessage onDirectMessage
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnDirectMessage(OnDirectMessage onDirectMessage);

    /**
     * @since 1.0.1
     * @param onUserListUpdate onUserListUpdate
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnUserListUpdate(OnUserListUpdate onUserListUpdate);

    /**
     * @since 1.0.1
     * @param onUserListSubscription onUserListSubscription
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnUserListSubscription(OnUserListSubscription onUserListSubscription);

    /**
     * @since 1.0.1
     * @param onUserListUnsubscription onUserListUnsubscription
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnUserListUnsubscription(OnUserListUnsubscription onUserListUnsubscription);

    /**
     * @since 1.0.1
     * @param onUserListMemberAddition onUserListMemberAddition
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnUserListMemberAddition(OnUserListMemberAddition onUserListMemberAddition);

    /**
     * @since 1.0.1
     * @param onUserListMemberDeletion onUserListMemberDeletion
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnUserListMemberDeletion(OnUserListMemberDeletion onUserListMemberDeletion);

    /**
     * @since 1.0.1
     * @param onUserListDeletion onUserListDeletion
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnUserListDeletion(OnUserListDeletion onUserListDeletion);

    /**
     * @since 1.0.1
     * @param onUserListCreation onUserListCreation
     * @return メソッドチェーンのためのthis
     */
    UserStream removeOnUserListCreation(OnUserListCreation onUserListCreation);

}
