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
        AccountContainer container = loadContainer();
        MutableList<Account> result = container.getAccounts().select(a -> !a.equals(account)).toList();
        result.add(account);
        AccountContainer accountContainer = new AccountContainer(result);
        saveContainer(accountContainer);
    }

    @Override
    public void save(Iterable<Account> accounts) {
        //TODO
    }

    @Override
    public void delete(Account account) {
        //TODO
    }

    @Override
    public void delete(Iterable<Account> accounts) {
        //TODO
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
