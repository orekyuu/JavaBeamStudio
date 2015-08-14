package net.orekyuu.javatter.api.plugin;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.service.Service;

import java.nio.file.Path;

/**
 * プラグインの機能を提供するサービス
 */
@Service
public interface PluginService {

    /**
     * プラグインをロードする
     * @param path プラグインのPath
     * @param classLoader ClassLoader
     * @return プラグインの情報
     * @since 1.0.0
     */
    PluginInfo load(Path path, PluginClassLoader classLoader);

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
     * @param classLoader ClassLoader
     * @return プラグインの情報
     * @since 1.0.0
     */
    ImmutableList<PluginInfo> loadPlugins(Path path, PluginClassLoader classLoader);
}
