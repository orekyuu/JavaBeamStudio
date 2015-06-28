package net.orekyuu.javatter.core.service;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.column.ColumnState;
import net.orekyuu.javatter.api.service.ColumnStateStorageService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ColumnStateStorageServiceImpl implements ColumnStateStorageService {

    private static final Logger logger = Logger.getLogger(ColumnStateStorageServiceImpl.class.getName());

    @Override
    public void save(ImmutableList<ColumnState> columnStates) {
        logger.log(Level.INFO, columnStates.toString());
    }

    @Override
    public ImmutableList<ColumnState> loadColumnStates() {
        logger.log(Level.INFO, "load");
        return Lists.immutable.of();
    }
}
