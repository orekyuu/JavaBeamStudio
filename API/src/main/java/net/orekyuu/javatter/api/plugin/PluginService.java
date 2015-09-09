package net.orekyuu.javatter.api.plugin;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.service.Service;

import java.nio.file.Path;

/**
 * プラグインの機能を提供するサービス
 * @since 1.0.0
 */
@Service
public interface PluginService {

    /**
     * プラグインをロードする
     * @param path プラグインのPath
     * @return プラグインの情報
     * @since 1.0.0
     */
    PluginInfo load(Path path);

    /**
     * 指定されたファイルがプラグインかどうかを判定します。
     * @param path 調べるファイルのPath
     * @return プラグインとして読み込み可能であればtrue
     * @since 1.0.0
     */
    boolean isPlugin(Path path);

    /**
     * 指定されたPath以下のディレクトリを検索し、見つかったプラグインをロードします。
     * @param path 検索するディレクトリ
     * @return プラグインの情報
     * @since 1.0.0
     */
    ImmutableList<PluginInfo> loadPlugins(Path path);

    /**
     * プラグインが存在しているかをチェックします。
     * @since 1.0.0
     * @param pluginID プラグインID
     * @return 存在していればtrue
     */
    boolean exist(String pluginID);

    /**
     * 全てのプラグインの情報を取得します。
     * @since 1.0.0
     * @return ロード済みの全てのプラグイン情報
     */
    ImmutableList<PluginInfo> getAllPluginInfo();

    /**
     * プラグインをロードするClassLoaderを返します。
     * @since 1.0.0
     * @return ClassLoader
     */
    ClassLoader getPluginClassLoader();
}
