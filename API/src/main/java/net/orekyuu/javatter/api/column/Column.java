package net.orekyuu.javatter.api.column;

public interface Column {

    void restoration(ColumnState columnState);

    String getPluginId();

    String getColumnId();

    default ColumnState newColumnState() {
        return new ColumnState(getPluginId(), getColumnId());
    }
}
