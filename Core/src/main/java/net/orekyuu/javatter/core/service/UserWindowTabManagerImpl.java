package net.orekyuu.javatter.core.service;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.block.factory.Functions;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.plugin.PluginService;
import net.orekyuu.javatter.api.userwindow.UserWindowTabManager;
import net.orekyuu.javatter.api.util.lookup.Lookup;
import net.orekyuu.javatter.core.JavaBeamStudio;

import java.net.URL;

public class UserWindowTabManagerImpl implements UserWindowTabManager {

    private MutableList<String> paths = Lists.mutable.empty();

    @Override
    public void registerTab(String fxmlPath) {
        paths.add(fxmlPath);
    }

    @Override
    public ImmutableList<URL> registeredTabFxmlURLs() {
        PluginService service = Lookup.lookup(PluginService.class);
        ClassLoader classLoader = service.getPluginClassLoader();
        return paths.collect(Functions.throwing(classLoader::getResource)).toImmutable();
    }
}
