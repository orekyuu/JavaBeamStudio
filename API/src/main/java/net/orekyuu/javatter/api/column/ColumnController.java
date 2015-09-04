package net.orekyuu.javatter.api.column;

/**
 * カラムのコントローラです。<br>
 * カラムのFXMLのコントローラには必ずこのインターフェイスを実装する必要があります。
 *
 * @since 1.0.0
 */
public interface ColumnController {

    /**
     * ColumnStateからViewの状態を復元します。<br>
     * デフォルトの状態を表すnullが与えられることがあります。
     *
     * @param columnState カラムの状態
     * @since 1.0.0
     */
    void restoration(ColumnState columnState);

    /**
     * プラグインID
     *
     * @return プラグインID
     * @since 1.0.0
     */
    String getPluginId();

    /**
     * カラムID
     *
     * @return カラムID
     * @since 1.0.0
     */
    String getColumnId();

    /**
     * カラムが閉じられるときのイベントです。<br>
     * 後処理を行ってください。
     *
     */
    void onClose();

    /**
     * 現在のカラムの状態を返します
     * @return カラムの状態
     */
    ColumnState getState();

    /**
     * デフォルトのカラムの状態を作成します。
     *
     * @return デフォルトのカラムの状態
     * @since 1.0.0
     */
    default ColumnState defaultColumnState() {
        return new ColumnState(getPluginId(), getColumnId());
    }
}
