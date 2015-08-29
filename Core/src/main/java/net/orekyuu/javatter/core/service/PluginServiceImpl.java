package net.orekyuu.javatter.core.service;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.impl.factory.Lists;
import com.gs.collections.impl.factory.Maps;
import net.orekyuu.javatter.api.plugin.PluginClassLoader;
import net.orekyuu.javatter.api.plugin.PluginInfo;
import net.orekyuu.javatter.api.plugin.PluginService;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

public class PluginServiceImpl implements PluginService {

    private static Map<String, PluginInfo> map = Maps.mutable.of();
    private static PluginClassLoader classLoader = new PluginClassLoader(ClassLoader.getSystemClassLoader());
    public static final PluginInfo BUILD_IN;

    static {
        BUILD_IN = new PluginInfo("BuildIn", "ビルドイン", "orekyuu", "javatter.orekyuu.net", null, null, null, "1.0.0", "", null);
    }

    public PluginServiceImpl() {
        map.put(BUILD_IN.getPluginId(), BUILD_IN);
    }

    @Override
    public PluginInfo load(Path path) {
        if (isPlugin(path)) {
            try {
                JarFile jarFile = new JarFile(path.toFile());
                Manifest manifest = jarFile.getManifest();
                PluginInfo pluginInfo = parseManifest(manifest);
                map.put(pluginInfo.getPluginId(), pluginInfo);
                classLoader.addPath(path);
                return pluginInfo;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean isPlugin(Path path) {
        if (!path.getFileName().toString().endsWith(".jar")) {
            return false;
        }

        try {
            JarFile jarFile = new JarFile(path.toFile());
            Manifest manifest = jarFile.getManifest();
            if (manifest == null) {
                return false;
            }
            Attributes attributes = manifest.getMainAttributes();
            if (attributes == null) {
                return false;
            }

            return attributes.getValue("Plugin-ID") != null;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ImmutableList<PluginInfo> loadPlugins(Path path) {
        try {
            List<PluginInfo> pluginInfos = Files.walk(path)
                    .filter(this::isPlugin)
                    .map(this::load)
                    .collect(Collectors.toList());
            return Lists.immutable.ofAll(pluginInfos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Lists.immutable.empty();
    }

    @Override
    public boolean exist(String pluginID) {
        return map.containsKey(pluginID);
    }

    @Override
    public ImmutableList<PluginInfo> getAllPluginInfo() {
        return Lists.immutable.ofAll(map.values());
    }

    @Override
    public ClassLoader getPluginClassLoader() {
        return classLoader;
    }

    private PluginInfo parseManifest(Manifest manifest) {
        Attributes attributes = manifest.getMainAttributes();
        String pluginId = attributes.getValue("Plugin-ID");
        String pluginName = attributes.getValue("Plugin-Name");
        String author = attributes.getValue("Author");
        String authorWebLink = attributes.getValue("Author-Web");
        String repository = attributes.getValue("Repository");
        String repositoryLink = attributes.getValue("Repository-Web");
        String bugTrackWebLink = attributes.getValue("Bug-Track-Web");
        String version = attributes.getValue("Plugin-Version");
        String main = attributes.getValue("Plugin-Class");
        String pluginPage = attributes.getValue("Plugin-Page");
        return new PluginInfo(pluginId, pluginName, author, authorWebLink, repository, repositoryLink,
                bugTrackWebLink, version, main, pluginPage);
    }
}
