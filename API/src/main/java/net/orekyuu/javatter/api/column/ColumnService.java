package net.orekyuu.javatter.api.column;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.column.events.ColumnAddListener;
import net.orekyuu.javatter.api.column.events.ColumnRemoveListener;
import net.orekyuu.javatter.api.service.Service;

/**
 * 開いているカラムを制御するServiceです。
 * @since 1.0.0
 */
@Service
public interface ColumnService {

    /**
     * カラムを追加します。
     * @param column 追加するカラム
     * @since 1.0.0
     */
    void addColumn(Column column);

    /**
     * カラムが追加された時のイベント
     *
     * @param listener リスナ
     * @since 1.0.0
     */
    void addColumnEvent(ColumnAddListener listener);

    /**
     * カラムを削除します。
     * @param column 削除するカラム
     * @since 1.0.0
     */
    void removeColumn(Column column);

    /**
     * カラムが削除された時のイベント
     * @param listener リスナ
     * @since 1.0.0
     */
    void removeColumnEvent(ColumnRemoveListener listener);

    /**
     * 開いているカラムのリストを返します
     *
     * @return 開いているカラム
     * @since 1.0.0
     */
    ImmutableList<ColumnController> getAllColumn();
}
