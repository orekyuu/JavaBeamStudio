package net.orekyuu.javatter.api.service;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.column.ColumnFactory;

import java.util.Optional;

/**
 * 使用できるカラムを管理するManagerです。
 */
public interface ColumnManager {

    void registerColumn(String pluginId, String columnName, String fxmlPath);

    void unregisterColumn(String pluginId, String columnName);

    /**
     * カラムのIDとプラグインIDから登録されているカラムのFactoryを返します。
     *
     * @param pluginId プラグインID
     * @param columnId カラムID
     * @return カラムのファクトリ
     */
    Optional<ColumnFactory> findByPluginIdAndColumnId(String pluginId, String columnId);

    ImmutableList<ColumnFactory> findAll();

}
