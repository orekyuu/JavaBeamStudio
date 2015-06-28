package net.orekyuu.javatter.api.column;

import com.gs.collections.api.collection.MutableCollection;
import com.gs.collections.api.multimap.MutableMultimap;
import com.gs.collections.impl.factory.Lists;
import com.gs.collections.impl.factory.Multimaps;

import java.io.Serializable;

public final class ColumnState implements Serializable {

    private final MutableMultimap<String, Serializable> data = Multimaps.mutable.bag.with();
    private final String pluginId;
    private final String columnId;

    public ColumnState(String pluginId, String columnId) {
        this.pluginId = pluginId;
        this.columnId = columnId;
    }

    public void addData(String key, Serializable value) {
        data.put(key, value);
    }

    public void removeData(String key, Serializable value) {
        data.remove(key, value);
    }

    public void setData(String key, Serializable value) {
        data.replaceValues(key, Lists.immutable.of(value));
    }

    public void removeKeyData(String key) {
        data.removeAll(key);
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> MutableCollection<T> getData(String key) {
        MutableCollection<T> collection = (MutableCollection<T>) data.get(key);

        return collection;
    }

    public void clear() {
        data.clear();
    }

    public String getPluginId() {
        return pluginId;
    }

    public String getColumnId() {
        return columnId;
    }
}
