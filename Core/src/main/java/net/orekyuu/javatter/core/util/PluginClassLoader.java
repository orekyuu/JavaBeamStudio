package net.orekyuu.javatter.core.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

/**
 * プラグインを読み込むために使用するクラスローダー
 */
public final class PluginClassLoader extends URLClassLoader {
    public PluginClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    public void addPath(Path path) throws MalformedURLException {
        addURL(path.toUri().toURL());
    }
}
