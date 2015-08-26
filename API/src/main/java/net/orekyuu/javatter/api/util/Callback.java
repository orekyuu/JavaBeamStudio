package net.orekyuu.javatter.api.util;

/**
 * 非同期操作などのコールバック
 * @param <T> コールバックの型
 */
@FunctionalInterface
public interface Callback<T> {

    /**
     * コールバックの結果
     * @since 1.0.0
     * @param result コールバックの結果
     */
    void result(T result);
}
