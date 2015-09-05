package net.orekyuu.javatter.core.service;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.multimap.ImmutableMultimap;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.account.Account;
import net.orekyuu.javatter.api.service.AccountStorageService;
import net.orekyuu.javatter.core.account.AccountContainer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

public class AccountStorageServiceImpl implements AccountStorageService {

    private static final Path path = Paths.get("account");

    @Override
    public <T extends Account> Optional<T> find(Class<T> accountType, String userName) {
        return Optional.ofNullable(findByType(accountType)
                .select(account -> account.getId().equals(userName))
                .getFirst());
    }

    @Override
    public <T> ImmutableList<T> findByType(Class<T> accountType) {
        AccountContainer container = loadContainer();

        if (container == null) {
            return Lists.immutable.of();
        }
        return container.getAccounts()
                .select(account -> account != null && accountType.isAssignableFrom(account.getClass()))
                .collect(account -> (T) account);
    }

    @Override
    public ImmutableMultimap<Class<? extends Account>, Account> findAllAccount() {
        AccountContainer container = loadContainer();
        return container.getAccounts().groupBy(a -> a.getClass());
    }

    @Override
    public void save(Account account) {
        save(Collections.singletonList(account));
    }

    @Override
    public void save(Iterable<Account> accounts) {
        AccountContainer container = loadContainer();
        MutableList<Account> select = container.getAccounts().select(a -> {
            for (Account account : accounts) {
                if (Objects.equals(account, a)) {
                    return false;
                }
            }
            return true;
        }).toList();
        accounts.forEach(select::add);
        AccountContainer accountContainer = new AccountContainer(select);
        saveContainer(accountContainer);
    }

    @Override
    public void delete(Account account) {
        delete(Collections.singletonList(account));
    }

    @Override
    public void delete(Iterable<Account> accounts) {
        AccountContainer container = loadContainer();
        MutableList<Account> list = container.getAccounts().toList();
        list.removeAllIterable(accounts);
        AccountContainer accountContainer = new AccountContainer(list);
        saveContainer(accountContainer);
    }

    private AccountContainer loadContainer() {
        AccountContainer container = null;
        //存在しなければファイル作成
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
                container = new AccountContainer(new ArrayList<>());
                saveContainer(container);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {
            container = (AccountContainer) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return container;
    }

    private void saveContainer(AccountContainer container) {
        //存在しなければファイル作成
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            outputStream.writeObject(container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
