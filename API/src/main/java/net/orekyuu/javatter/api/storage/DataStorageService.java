package net.orekyuu.javatter.api.storage;

import net.orekyuu.javatter.api.service.Service;

import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * 永続化したデータにアクセスするためのサービスです。
 * プリミティブな型とString、Listのみのフィールドを持つクラスのみ保存可能です。
 */
@Service
public interface DataStorageService {

    /**
     * データをファイルシステムからロードします。
     * 存在しない場合はnullが返ります。
     * @since 1.0.0
     * @throws java.io.UncheckedIOException ファイルの読み込み時に例外が発生した場合
     * @throws net.orekyuu.javatter.api.plugin.PluginNotFoundException 指定したプラグインIDに対応するプラグインが見つからなかった時
     * @param pluginID プラグインID
     * @param dataClass データクラス
     * @param <T> データクラスの型
     * @return 復元した情報
     */
    <T> T find(String pluginID, Class<T> dataClass);

    /**
     * データをファイルシステムからロードします。
     * 存在しない場合はdefaultValueが返ります。
     * @since 1.0.0
     * @throws java.io.UncheckedIOException ファイルの読み込み時に例外が発生した場合
     * @throws net.orekyuu.javatter.api.plugin.PluginNotFoundException 指定したプラグインIDに対応するプラグインが見つからなかった時
     * @param pluginID プラグインID
     * @param dataClass データクラス
     * @param defaultValue 見つからなかった時のデフォルト値
     * @param <T> データクラスの型
     * @return 復元した情報
     */
    <T> T find(String pluginID, Class<T> dataClass, T defaultValue);

    /**
     * データをファイルシステムからロードします。
     * 存在しない場合はdefaultValueSupplierを実行し、その結果を返します。
     * @since 1.0.0
     * @throws java.io.UncheckedIOException ファイルの読み込み時に例外が発生した場合
     * @throws net.orekyuu.javatter.api.plugin.PluginNotFoundException 指定したプラグインIDに対応するプラグインが見つからなかった時
     * @param pluginID プラグインID
     * @param dataClass データクラス
     * @param defaultValueSupplier 見つからなかった時のデフォルト値を生成する関数
     * @param <T> データクラスの型
     * @return 復元した情報
     */
    <T> T find(String pluginID, Class<T> dataClass, Supplier<T> defaultValueSupplier);

    /**
     * データを保存します。
     * @since 1.0.0
     * @throws java.io.UncheckedIOException ファイルの書き込み時に例外が発生した場合
     * @throws net.orekyuu.javatter.api.plugin.PluginNotFoundException 指定したプラグインIDに対応するプラグインが見つからなかった時
     * @param pluginID プラグインID
     * @param obj 保存する情報
     */
    void save(String pluginID, Object obj);

    /**
     * データを削除します。
     * 存在しなければ何も行いません。
     * @since 1.0.0
     * @throws java.io.UncheckedIOException ファイルの書き込み時に例外が発生した場合
     * @throws net.orekyuu.javatter.api.plugin.PluginNotFoundException 指定したプラグインIDに対応するプラグインが見つからなかった時
     * @param pluginID プラグインID
     * @param dataClass データの型
     */
    void delete(String pluginID, Class<?> dataClass);

    /**
     * データが保存されるディレクトリを返します。
     * @since 1.0.0
     * @throws net.orekyuu.javatter.api.plugin.PluginNotFoundException 指定したプラグインIDに対応するプラグインが見つからなかった時
     * @param pluginID プラグインID
     * @return データが保存されるディレクトリ
     */
    Path getStorageDirectory(String pluginID);
}
