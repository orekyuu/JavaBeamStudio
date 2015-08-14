package net.orekyuu.javatter.api.service;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.multimap.ImmutableMultimap;
import net.orekyuu.javatter.api.account.Account;

import java.util.Optional;

/**
 * アカウントを管理するサービス
 */
@Service
public interface AccountStorageService {

    /**
     * アカウントを検索します
     * @param accountType アカウントのClass
     * @param id ID
     * @param <T> アカウントの型
     * @return アカウントの型とIDに一致するアカウント
     * @since 1.0.0
     */
    <T extends Account> Optional<T> find(Class<T> accountType, String id);

    /**
     * 型に一致する全てのアカウントを返します。
     * @param accountType アカウントのClass
     * @param <T> アカウントの型
     * @return 型に一致する全てのアカウント
     * @since 1.0.0
     */
    <T> ImmutableList<T> findByType(Class<T> accountType);

    /**
     * @return 全てのアカウント
     * @since 1.0.0
     */
    ImmutableMultimap<Class<? extends Account>, Account> findAllAccount();

    /**
     * アカウントを保存します
     * @param account 保存するアカウント
     * @since 1.0.0
     */
    void save(Account account);

    /**
     * アカウントを保存します
     * @param accounts 保存するアカウント
     * @since 1.0.0
     */
    void save(Iterable<Account> accounts);

    /**
     * アカウントを削除します
     * @param account 削除するアカウント
     * @since 1.0.0
     */
    void delete(Account account);

    /**
     * アカウントを削除します
     * @param accounts 削除するアカウント
     * @since 1.0.0
     */
    void delete(Iterable<Account> accounts);
}
