package net.orekyuu.javatter.api.service;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.twitter.TwitterUser;

import java.util.Optional;

public interface TwitterUserService {

    Optional<TwitterUser> selectedAccount();

    void select(TwitterUser account);

    ImmutableList<TwitterUser> allUser();

    void register(TwitterAccount account);

    Optional<TwitterUser> findTwitterUser(String userId);
}
