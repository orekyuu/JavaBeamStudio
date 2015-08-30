package net.orekyuu.javatter.core.service;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.impl.factory.Lists;
import com.gs.collections.impl.factory.Maps;
import net.orekyuu.javatter.api.notification.NotificationService;
import net.orekyuu.javatter.api.plugin.PluginClassLoader;
import net.orekyuu.javatter.api.plugin.PluginInfo;
import net.orekyuu.javatter.api.plugin.PluginService;
import net.orekyuu.javatter.api.service.EnvironmentService;
import net.orekyuu.javatter.api.util.lookup.Lookup;
import net.orekyuu.javatter.core.util.VersionComparator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class PluginServiceImpl implements PluginService {

    private static Map<String, PluginInfo> map = Maps.mutable.of();
    private static PluginClassLoader classLoader = new PluginClassLoader(ClassLoader.getSystemClassLoader());
    public static final PluginInfo BUILD_IN;

    static {
        BUILD_IN = new PluginInfo("BuildIn", "ビルドイン", "orekyuu", "javatter.orekyuu.net", null, null, null, "1.0.0", "", null, "1.0.0");
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
                VersionComparator comparator = new VersionComparator();
                EnvironmentService service = Lookup.lookup(EnvironmentService.class);
                if (comparator.compare(service.getJavaBeamStudioApiVersion(), pluginInfo.getApiVersion()) <= 0) {
                    map.put(pluginInfo.getPluginId(), pluginInfo);
                    classLoader.addPath(path);
                } else {
                    NotificationService notificationService = Lookup.lookup(NotificationService.class);
                    notificationService.showWarning("プラグインの読み込みをスキップ",
                            "APIバージョンが古いため読み込みをスキップしました。\nPlugin ID:"+
                                    pluginInfo.getPluginId() + "\nAPI Version: " +
                                    pluginInfo.getApiVersion());
                }
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
            Files.walk(path)
                    .filter(this::isPlugin)
                    .forEach(this::load);
            return getAllPluginInfo();
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
        String apiVersion = attributes.getValue("Min-API-Version");
        return new PluginInfo(pluginId, pluginName, author, authorWebLink, repository, repositoryLink,
                bugTrackWebLink, version, main, pluginPage, apiVersion);
    }
}
