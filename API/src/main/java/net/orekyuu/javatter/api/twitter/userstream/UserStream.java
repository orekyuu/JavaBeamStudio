package net.orekyuu.javatter.api.twitter.userstream;

import net.orekyuu.javatter.api.twitter.userstream.events.*;

/**
 * @since 1.0.0
 */
public interface UserStream {

    UserStream onStatus(OnStatus onStatus);

    UserStream onMention(OnMention onMention);

    UserStream onException(OnException onException);

    UserStream onUserProfileUpdate(OnUserProfileUpdate onUserProfileUpdate);

    UserStream onBlock(OnBlock onBlock);

    UserStream onFavorite(OnFavorite onFavorite);

    UserStream onFollow(OnFollow onFollow);

    UserStream onUnblock(OnUnblock onUnblock);

    UserStream onUnfollow(OnUnfollow onUnfollow);

    UserStream onUnfavorite(OnUnfavorite onUnfavorite);

    UserStream onDirectMessage(OnDirectMessage onDirectMessage);

    UserStream onUserListUpdate(OnUserListUpdate onUserListUpdate);

    UserStream onUserListSubscription(OnUserListSubscription onUserListSubscription);

    UserStream onUserListUnsubscription(OnUserListUnsubscription onUserListUnsubscription);

    UserStream onUserListMemberAddition(OnUserListMemberAddition onUserListMemberAddition);

    UserStream onUserListMemberDeletion(OnUserListMemberDeletion onUserListMemberDeletion);

    UserStream onUserListDeletion(OnUserListDeletion onUserListDeletion);

    UserStream onUserListCreation(OnUserListCreation onUserListCreation);


}
