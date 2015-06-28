package net.orekyuu.javatter.api.util.lookup;

/**
 * 依存関係を解決するための実装を表すインターフェイスです。
 *
 * @since 1.0.0
 */
public interface Lookuper {

    /**
     * 引数のクラスに紐付けされたクラスの実装を返します。
     * @param clazz インターフェイス
     * @param <T> インターフェイスの型
     * @return 引数に紐付けされたクラスのインスタンス
     * @since 1.0.0
     */
    <T> T lookup(Class<T> clazz);

    /**
     * 引数のインスタンスで{@link javax.inject.Inject}が付与されているフィールドの依存関係を解決します。
     * @param object 注入するインスタンス
     * @since 1.0.0
     */
    void inject(Object object);
}
