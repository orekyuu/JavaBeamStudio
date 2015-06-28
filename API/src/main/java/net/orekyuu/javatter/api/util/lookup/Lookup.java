package net.orekyuu.javatter.api.util.lookup;

/**
 * 依存関係を解決するためのクラスです。<br>
 * このクラスが提供するメソッドを使用する前には必ず{@link Lookup#setLookuper(Lookuper)}が呼び出される必要があります。
 *
 * @since 1.0.0
 */
public final class Lookup {

    private static Lookuper lookuper;

    //staticユーティリティクラスなのでコンストラクタは呼び出せません
    private Lookup() {
        throw new UnsupportedOperationException();
    }

    /**
     * 依存関係を解決するための実装を設定します。<br>
     * アプリケーションの実装で呼び出される必要があります。
     * @param lookuper 依存関係を解決するための実装
     * @since 1.0.0
     * @throws IllegalStateException すでに初期化済みの時
     */
    public static void setLookuper(Lookuper lookuper) {
        //この実装が気に食わない。あとで何とかしたい。
        //というかこのパッケージの存在自体がちょっとアレ。せめて名前かえるべき？
        if (Lookup.lookuper != null) {
            throw new IllegalStateException("lookuper is initialized");
        }
        Lookup.lookuper = lookuper;
    }

    /**
     * 引数のクラスに紐付けされたクラスの実装を返します。
     * @param t インターフェイス
     * @param <T> インターフェイスの型
     * @return 引数に紐付けされたクラスのインスタンス
     * @throws IllegalArgumentException {@link Lookup#setLookuper(Lookuper)}が呼び出されていない時
     * @since 1.0.0
     */
    public static <T> T lookup(Class<T> t) {
        checkInitialize();
        return lookuper.lookup(t);
    }

    /**
     * 引数のインスタンスで{@link javax.inject.Inject}が付与されているフィールドの依存関係を解決します。
     *
     * @param object 注入するインスタンス
     * @throws IllegalArgumentException {@link Lookup#setLookuper(Lookuper)}が呼び出されていない時
     * @since 1.0.0
     */
    public static void inject(Object object) {
        checkInitialize();
        lookuper.inject(object);
    }

    /**
     * 初期化済みかを返すメソッド
     *
     * @return 初期化済みであればtrue
     * @since 1.0.0
     */
    public static boolean isInitialized() {
        return lookuper != null;
    }

    private static void checkInitialize() {
        if (!isInitialized()) {
            throw new IllegalStateException("Lookup is not Initialized. Need to call Lookup#setLookuper.");
        }
    }
}
