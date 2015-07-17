package net.orekyuu.javatter.api.plugin;

import com.gs.collections.api.list.ImmutableList;
import net.orekyuu.javatter.api.service.Service;

import java.nio.file.Path;

@Service
public interface PluginService {

    PluginInfo load(Path path, PluginClassLoader classLoader);

    boolean isPlugin(Path path);

    ImmutableList<PluginInfo> loadPlugins(Path path, PluginClassLoader classLoader);
}
