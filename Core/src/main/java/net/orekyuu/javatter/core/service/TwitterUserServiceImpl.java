package net.orekyuu.javatter.core.service;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.account.TwitterAccount;
import net.orekyuu.javatter.api.service.TwitterUserService;
import net.orekyuu.javatter.api.twitter.TwitterUser;
import net.orekyuu.javatter.core.twitter.TwitterUserImpl;

import java.util.Objects;
import java.util.Optional;

public class TwitterUserServiceImpl implements TwitterUserService {

    private static TwitterUser selectedUser;
    private static final MutableList<TwitterUser> registeredUser = Lists.mutable.of();

    @Override
    public Optional<TwitterUser> selectedAccount() {
        return Optional.ofNullable(selectedUser);
    }

    @Override
    public void select(TwitterUser user) {
        selectedUser = user;
    }

    @Override
    public ImmutableList<TwitterUser> allUser() {
        return registeredUser.toImmutable();
    }

    @Override
    public void register(TwitterAccount account) {
        TwitterUserImpl user = new TwitterUserImpl();
        user.authentication(account);
        registeredUser.add(user);
    }

    @Override
    public Optional<TwitterUser> findTwitterUser(String userId) {
        return Optional.ofNullable(registeredUser.detect(user -> Objects.equals(user.getUser().getScreenName(), userId)));
    }
}
