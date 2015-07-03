/**
 * カラムを操作するためのAPIパッケージです。<br>
 * <p>
 * <h1>カラムの追加</h1>
 * Columnを新規に追加するためには、{@link net.orekyuu.javatter.api.service.ColumnManager#registerColumn(java.lang.String, java.lang.String, java.lang.String)}でプラグインIDとカラムIDとFXMLのパスを与えて登録します。<br>
 * FXMLのルートノードにはfx:controllerでColumnControllerを実装するクラスを指定します。<br>
 * <p>
 * <h1>カラムの状態を永続化する</h1>
 * カラムの情報はColumnStateに保存されます。<br>
 * {@link net.orekyuu.javatter.api.column.ColumnController#restoration(net.orekyuu.javatter.api.column.ColumnState)}がコントローラ生成時に呼び出されるため、引数の{@link net.orekyuu.javatter.api.column.ColumnState}から復元してください。<br>
 */
package net.orekyuu.javatter.api.column;
