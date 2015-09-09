package net.orekyuu.javatter.api.account;

/**
 * Twitterの認証用アカウント
 * @since 1.0.0
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
