package net.orekyuu.javatter.api.twitter;

import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.twitter.model.User;
import net.orekyuu.javatter.api.twitter.userstream.UserStream;

/**
 * Twitterクライアントを利用するユーザー
 *
 * @since 1.0.0
 */
public interface TwitterUser extends FavoriteControl, RetweetControl, TweetControl, TweetResource {

    /**
     * 認証を行います。
     * @param account 認証用の情報
     * @since 1.0.0
     */
    void authentication(TwitterAccount account);

    /**
     * このユーザーのユーザーストリームを返します。
     * @return このユーザーのユーザーストリーム
     * @since 1.0.0
     */
    UserStream userStream();

    /**
     * この使用者のTwitterのユーザー情報を返します。
     * @return Twitterのユーザー情報
     * @since 1.0.0
     */
    User getUser();

    /**
     * このユーザーを破棄する場合の後処理を行います。
     * @since 1.0.0
     */
    void dispose();
}
