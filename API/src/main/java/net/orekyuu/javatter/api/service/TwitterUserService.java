package net.orekyuu.javatter.api.service;

import com.gs.collections.api.list.ImmutableList;
import javafx.beans.property.ObjectProperty;
import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.twitter.TwitterUser;

import java.util.Optional;

/**
 * Twitterを利用するユーザーへアクセスするためのサービス
 * @since 1.0.0
 */
@Service
public interface TwitterUserService {

    /**
     * ツイートのために選択されているアカウント
     * @since 1.0.0
     * @return 選択されているアカウント
     */
    Optional<TwitterUser> selectedAccount();

    /**
     * @since 1.0.0
     * @return 選択されているユーザーのプロパティ
     */
    ObjectProperty<TwitterUser> selectedAccountProperty();

    /**
     * ツイートのためのアカウントを指定されたものに変更します
     * @since 1.0.0
     * @param account 変更したいアカウント
     */
    void select(TwitterUser account);

    /**
     * 登録されている全てのユーザーを返します。
     * @since 1.0.0
     * @return すべてのユーザー
     */
    ImmutableList<TwitterUser> allUser();

    /**
     * アカウントを登録します。
     * @since 1.0.0
     * @param account 登録するアカウント
     */
    void register(TwitterAccount account);

    /**
     * IDから登録されているユーザーを検索します。
     * @since 1.0.0
     * @param userId ユーザーID
     * @return IDに一致するユーザー
     */
    Optional<TwitterUser> findTwitterUser(String userId);
}
