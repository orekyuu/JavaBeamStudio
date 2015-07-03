package net.orekyuu.javatter.api.column;

import com.gs.collections.api.collection.MutableCollection;
import com.gs.collections.api.multimap.MutableMultimap;
import com.gs.collections.impl.factory.Lists;
import com.gs.collections.impl.factory.Multimaps;

import java.io.Serializable;

/**
 * カラムの状態を保持するクラス
 *
 * @since 1.0.0
 */
public final class ColumnState implements Serializable {

    private final MutableMultimap<String, Serializable> data = Multimaps.mutable.bag.with();
    private final String pluginId;
    private final String columnId;

    /**
     * カラムの状態を作成します
     *
     * @param pluginId プラグインID
     * @param columnId カラムID
     * @since 1.0.0
     */
    public ColumnState(String pluginId, String columnId) {
        this.pluginId = pluginId;
        this.columnId = columnId;
    }

    /**
     * 新しいデータを追加します。
     *
     * @param key   識別用のキー
     * @param value 保存する値
     * @since 1.0.0
     */
    public void addData(String key, Serializable value) {
        data.put(key, value);
    }

    /**
     * データを削除します。
     *
     * @param key   識別用のキー
     * @param value 削除するデータ
     * @since 1.0.0
     */
    public void removeData(String key, Serializable value) {
        data.remove(key, value);
    }

    /**
     * キーとデータを紐付けして保存します。
     *
     * @param key   識別用のキー
     * @param value 保存する値
     * @since 1.0.0
     */
    public void setData(String key, Serializable value) {
        data.replaceValues(key, Lists.immutable.of(value));
    }

    /**
     * キーに紐付けされたデータを削除します。
     *
     * @param key 識別用のキー
     * @since 1.0.0
     */
    public void removeKeyData(String key) {
        data.removeAll(key);
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> MutableCollection<T> getData(String key) {
        MutableCollection<T> collection = (MutableCollection<T>) data.get(key);

        return collection;
    }

    /**
     * すべてのデータを削除します。
     *
     * @since 1.0.0
     */
    public void clear() {
        data.clear();
    }

    /**
     * カラムのプラグインIDを返します。
     *
     * @return プラグインID
     * @since 1.0.0
     */
    public String getPluginId() {
        return pluginId;
    }

    /**
     * カラムのカラムIDを返します。
     *
     * @return カラムID
     * @since 1.0.0
     */
    public String getColumnId() {
        return columnId;
    }
}
