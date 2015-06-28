package net.orekyuu.javatter.core.service;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.column.Column;
import net.orekyuu.javatter.api.column.ColumnNodePair;
import net.orekyuu.javatter.api.service.ColumnService;
import net.orekyuu.javatter.api.service.events.ColumnAddListener;
import net.orekyuu.javatter.api.service.events.ColumnRemoveListener;

import java.util.logging.Logger;

public class ColumnServiceImpl implements ColumnService {

    private MutableList<ColumnNodePair> columns = Lists.mutable.of();
    private MutableList<ColumnAddListener> addListeners = Lists.mutable.of();
    private MutableList<ColumnRemoveListener> removeListeners = Lists.mutable.of();

    private static final Logger logger = Logger.getLogger(ColumnServiceImpl.class.getName());

    public ColumnServiceImpl() {
        MutableList<ColumnNodePair> list = Lists.mutable.of();
        columns = list.asSynchronized();
    }

    @Override
    public void addColumn(ColumnNodePair column) {
        logger.info("Add: " + column.getColumn().getPluginId() + ":" + column.getColumn().getColumnId());
        columns.add(column);
        addListeners.each(listener -> listener.onAddColumn(column));
    }

    @Override
    public void addColumnEvent(ColumnAddListener listener) {
        addListeners.add(listener);
    }

    @Override
    public void removeColumn(ColumnNodePair column) {
        columns.remove(column);
        removeListeners.each(listener -> listener.onRemoveColumn(column));
    }

    @Override
    public void removeColumnEvent(ColumnRemoveListener listener) {
        removeListeners.add(listener);
    }

    @Override
    public ImmutableList<Column> getAllColumn() {
        return columns.collect(ColumnNodePair::getColumn).toImmutable();
    }
}
