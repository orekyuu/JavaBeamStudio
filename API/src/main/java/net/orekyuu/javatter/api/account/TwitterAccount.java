package net.orekyuu.javatter.api.account;

/**
 * Twitterの認証用アカウント
 */
public interface TwitterAccount extends Account {

    /**
     * @return アクセストークン
     * @since 1.0.0
     */
    String getToken();

    /**
     * @return トークンシークレット
     * @since 1.0.0
     */
    String getTokenSecret();
}
