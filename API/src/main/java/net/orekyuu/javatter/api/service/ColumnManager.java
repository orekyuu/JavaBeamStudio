package net.orekyuu.javatter.api.service;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.column.ColumnFactory;

import java.util.Optional;

/**
 * 使用できるカラムを管理するManagerです。
 */
public interface ColumnManager {

    void registerColumn(String pluginId, String columnName, String fxmlPath, String name);

    void unregisterColumn(String pluginId, String columnName);

    ImmutableList<ColumnInfo> getAllColumnInfo();

    /**
     * カラムのIDとプラグインIDから登録されているカラムのFactoryを返します。
     *
     * @param pluginId プラグインID
     * @param columnId カラムID
     * @return カラムのファクトリ
     */
    Optional<ColumnFactory> findByPluginIdAndColumnId(String pluginId, String columnId);

    ImmutableList<ColumnFactory> findAll();

    final class ColumnInfo {
        private String pluginId;
        private String columnName;
        private String name;

        public ColumnInfo(String pluginId, String columnName, String name) {
            this.pluginId = pluginId;
            this.columnName = columnName;
            this.name = name;
        }

        public String getPluginId() {
            return pluginId;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getName() {
            return name;
        }
    }

}
