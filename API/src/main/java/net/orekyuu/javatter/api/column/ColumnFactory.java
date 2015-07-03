package net.orekyuu.javatter.api.column;

import java.io.IOException;

/**
 * カラムを作成するファクトリ
 *
 * @since 1.0.0
 */
@FunctionalInterface
public interface ColumnFactory {
    /**
     * カラムを作成します。<br>
     * 引数にはカラムの状態を与えます。完全に新規のカラムを作成する場合はnullを与えます。
     *
     * @param state カラムの状態(nullable)
     * @return 復元されたカラム
     * @throws IOException FXMLがロード出来なかった時
     * @since 1.0.0
     */
    Column newInstance(ColumnState state) throws IOException;
}
