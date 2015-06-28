package net.orekyuu.javatter.api.column;

import javafx.scene.Node;

/**
 * Twitterクライアントのカラムです。
 *
 * @since 1.0.0
 */
public final class Column {

    private final ColumnController columnController;
    private final Node root;

    /**
     * カラムのコントローラとノードをペアで持つColumnインスタンスを作成します。
     *
     * @param columnController コントローラ
     * @param root             ルートNode
     * @since 1.0.0
     */
    public Column(ColumnController columnController, Node root) {
        this.columnController = columnController;
        this.root = root;
    }

    /**
     * Columnのコントローラを返します。
     *
     * @return Columnのコントローラ
     * @since 1.0.0
     */
    public ColumnController getColumnController() {
        return columnController;
    }

    /**
     * Columnのルートノード
     *
     * @return Columnのルートノード
     * @since 1.0.0
     */
    public Node getRoot() {
        return root;
    }
}
