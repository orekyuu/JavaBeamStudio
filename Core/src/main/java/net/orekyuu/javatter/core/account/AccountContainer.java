package net.orekyuu.javatter.core.account;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.account.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountContainer implements Serializable {

    private List<Account> accounts;

    public AccountContainer(List<Account> accounts) {
        this.accounts = new ArrayList<>(accounts);
    }

    public ImmutableList<Account> getAccounts() {
        return Lists.immutable.ofAll(accounts);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountContainer{");
        sb.append("accounts=").append(accounts);
        sb.append('}');
        return sb.toString();
    }
}
