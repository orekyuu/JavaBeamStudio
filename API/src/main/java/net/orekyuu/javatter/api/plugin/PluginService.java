package net.orekyuu.javatter.api.plugin;

import com.gs.collections.api.list.ImmutableList;

import java.nio.file.Path;

public interface PluginService {

    PluginInfo load(Path path, PluginClassLoader classLoader);

    boolean isPlugin(Path path);

    ImmutableList<PluginInfo> loadPlugins(Path path, PluginClassLoader classLoader);
}
