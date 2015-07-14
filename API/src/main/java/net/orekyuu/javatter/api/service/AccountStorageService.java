package net.orekyuu.javatter.api.service;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.multimap.ImmutableMultimap;
import net.orekyuu.javatter.api.account.Account;

import java.util.Optional;

@Service
public interface AccountStorageService {

    <T extends Account> Optional<T> find(Class<T> accountType, String userName);

    <T> ImmutableList<T> findByType(Class<T> accountType);

    ImmutableMultimap<Class<? extends Account>, Account> findAllAccount();

    void save(Account account);

    void save(Iterable<Account> accounts);

    void delete(Account account);

    void delete(Iterable<Account> accounts);
}
