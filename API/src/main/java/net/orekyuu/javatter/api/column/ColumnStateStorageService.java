package net.orekyuu.javatter.api.column;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.service.Service;

/**
 * カラムの状態の保存/復元を行うServiceです。
 * @since 1.0.0
 */
@Service
public interface ColumnStateStorageService {

    /**
     * カラムの状態
     * @param columnStates カラムの状態
     * @since 1.0.0
     */
    void save(ImmutableList<ColumnState> columnStates);

    /**
     * カラムの情報をファイルシステムからロードします
     * @return 復元したカラムの状態
     * @since 1.0.0
     */
    ImmutableList<ColumnState> loadColumnStates();
}
