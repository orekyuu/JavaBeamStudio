package net.orekyuu.javatter.core.storage;

import net.orekyuu.javatter.api.plugin.PluginNotFoundException;
import net.orekyuu.javatter.api.plugin.PluginService;
import net.orekyuu.javatter.api.storage.DataStorageService;
import net.orekyuu.javatter.api.util.lookup.Lookup;

import javax.xml.bind.JAXB;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

public class DataStorageServiceImpl implements DataStorageService {
    @Override
    public <T> T find(String pluginID, Class<T> dataClass) {
        Path path = getFilePath(pluginID, dataClass);
        if (Files.notExists(path)) {
            throw new UncheckedIOException(
                    new FileNotFoundException("pluginID=" + pluginID + ", dataClass=" + dataClass.getName()));
        }
        try {
            return JAXB.unmarshal(Files.newBufferedReader(path), dataClass);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public <T> T find(String pluginID, Class<T> dataClass, T defaultValue) {
        Path path = getFilePath(pluginID, dataClass);
        if (Files.notExists(path)) {
            return defaultValue;
        }
        try {
            return JAXB.unmarshal(Files.newBufferedReader(path), dataClass);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public <T> T find(String pluginID, Class<T> dataClass, Supplier<T> defaultValueSupplier) {
        Path path = getFilePath(pluginID, dataClass);
        if (Files.notExists(path)) {
            return defaultValueSupplier.get();
        }
        try {
            return JAXB.unmarshal(Files.newBufferedReader(path), dataClass);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void save(String pluginID, Object obj) {
        Path path = getFilePath(pluginID, obj.getClass());
        try {
            JAXB.marshal(obj, Files.newBufferedWriter(path));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void delete(String pluginID, Class<?> dataClass) {
        Path path = getFilePath(pluginID, dataClass);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Path getStorageDirectory(String pluginID) {
        PluginService lookup = Lookup.lookup(PluginService.class);
        if (!lookup.exist(pluginID)) {
            throw new PluginNotFoundException(pluginID + " not found.");
        }
        return Paths.get("storage/" + pluginID);
    }

    private Path getFilePath(String pluginID, Class<?> clazz) {
        Path directory = getStorageDirectory(pluginID);
        return Paths.get(directory.toString(), clazz.getName() + ".xml");
    }
}
