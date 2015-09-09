package net.orekyuu.javatter.api.service;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.column.ColumnFactory;

import java.util.Optional;

/**
 * 使用できるカラムを管理するManagerです。
 * @since 1.0.0
 */
@Service
public interface ColumnManager {

    /**
     * カラムを登録する
     * @param pluginId プラグインID
     * @param columnName カラム名
     * @param fxmlPath FXMLのパス
     * @param name カラムの表示名
     * @since 1.0.0
     */
    void registerColumn(String pluginId, String columnName, String fxmlPath, String name);

    /**
     * カラムを削除する
     * @param pluginId プラグインID
     * @param columnName カラム名
     * @since 1.0.0
     */
    void unregisterColumn(String pluginId, String columnName);

    /**
     * 全てのカラム情報を取得する
     * @return 全てのカラム情報
     * @since 1.0.0
     */
    ImmutableList<ColumnInfo> getAllColumnInfo();

    /**
     * カラムのIDとプラグインIDから登録されているカラムのFactoryを返します。
     *
     * @param pluginId プラグインID
     * @param columnId カラムID
     * @return カラムのファクトリ
     * @since 1.0.0
     */
    Optional<ColumnFactory> findByPluginIdAndColumnId(String pluginId, String columnId);

    /**
     * 全てのカラム情報
     * @return 全ての絡む情報
     * @since 1.0.0
     */
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
