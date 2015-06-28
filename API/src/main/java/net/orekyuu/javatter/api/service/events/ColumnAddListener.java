package net.orekyuu.javatter.api.service.events;

import net.orekyuu.javatter.api.column.ColumnNodePair;

public interface ColumnAddListener {

    void onAddColumn(ColumnNodePair pair);
}
