package net.orekyuu.javatter.core.account;

import net.orekyuu.javatter.api.account.TwitterAccount;
import twitter4j.auth.AccessToken;

import java.util.Objects;

public class TwitterAccountImpl implements TwitterAccount {

    private String id;
    private String token;
    private String tokenSecret;

    public TwitterAccountImpl(AccessToken accessToken) {
        this.id = accessToken.getScreenName();
        this.token = accessToken.getToken();
        this.tokenSecret = accessToken.getTokenSecret();
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getTokenSecret() {
        return tokenSecret;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TwitterAccountImpl{");
        sb.append("id='").append(id).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", tokenSecret='").append(tokenSecret).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TwitterAccountImpl that = (TwitterAccountImpl) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(token, that.token) &&
                Objects.equals(tokenSecret, that.tokenSecret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, tokenSecret);
    }
}
