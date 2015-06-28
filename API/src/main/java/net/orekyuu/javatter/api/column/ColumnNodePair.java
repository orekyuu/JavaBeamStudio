package net.orekyuu.javatter.api.column;

import javafx.scene.Node;

public final class ColumnNodePair {

    private final Column column;
    private final Node root;

    public ColumnNodePair(Column column, Node root) {
        this.column = column;
        this.root = root;
    }

    public Column getColumn() {
        return column;
    }

    public Node getRoot() {
        return root;
    }
}
