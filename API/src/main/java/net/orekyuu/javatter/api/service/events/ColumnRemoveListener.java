package net.orekyuu.javatter.api.service.events;

import net.orekyuu.javatter.api.column.Column;

/**
 * カラムが削除された時のイベントリスナ
 * @since 1.0.0
 */
@FunctionalInterface
public interface ColumnRemoveListener {

    /**
     * カラムが削除された時に呼び出される
     * @param pair 削除されたカラム
     * @since 1.0.0
     */
    void onRemoveColumn(Column pair);
}
