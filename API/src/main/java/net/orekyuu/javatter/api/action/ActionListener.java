package net.orekyuu.javatter.api.action;

/**
 * アクションの動作を表すインターフェイスです。
 * @since 1.0.0
 * @param <T> Event
 */
@FunctionalInterface
public interface ActionListener<T> {

    /**
     * アクション実行時に呼び出されるメソッドです。
     * @since 1.0.0
     * @param value Event
     */
    void action(T value);
}
