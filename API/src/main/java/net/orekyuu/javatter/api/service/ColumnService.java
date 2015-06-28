package net.orekyuu.javatter.api.service;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.column.Column;
import net.orekyuu.javatter.api.column.ColumnNodePair;
import net.orekyuu.javatter.api.service.events.ColumnAddListener;
import net.orekyuu.javatter.api.service.events.ColumnRemoveListener;

/**
 * 開いているカラムを制御するServiceです。
 */
public interface ColumnService {

    /**
     * カラムを追加します。
     */
    void addColumn(ColumnNodePair column);

    /**
     * カラムが追加された時のイベント
     *
     * @param listener リスナ
     */
    void addColumnEvent(ColumnAddListener listener);

    /**
     * カラムを削除します。
     */
    void removeColumn(ColumnNodePair column);

    void removeColumnEvent(ColumnRemoveListener listener);

    /**
     * 開いているカラムのリストを返します
     *
     * @return 開いているカラム
     */
    ImmutableList<Column> getAllColumn();
}
