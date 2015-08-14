package net.orekyuu.javatter.api.account;

import java.io.Serializable;

/**
 * アカウントを表すインターフェイス
 */
public interface Account extends Serializable {

    /**
     * アカウントに一意なID
     * @return ID
     * @since 1.0.0
     */
    String getId();
}
