package net.orekyuu.javatter.api.column;

import java.io.IOException;

@FunctionalInterface
public interface ColumnFactory {
    ColumnNodePair newInstance(ColumnState state) throws IOException;
}
