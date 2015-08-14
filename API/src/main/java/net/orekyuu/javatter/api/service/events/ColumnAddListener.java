package net.orekyuu.javatter.api.service.events;

import net.orekyuu.javatter.api.column.Column;

/**
 * カラムが追加された時のイベントリスナ
 */
public interface ColumnAddListener {

    /**
     * カラムが追加された時に呼び出される
     * @param pair 追加されたカラム
     * @since 1.0.0
     */
    void onAddColumn(Column pair);
}
