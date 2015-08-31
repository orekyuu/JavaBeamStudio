package net.orekyuu.javatter.core.storage;

import net.orekyuu.javatter.api.plugin.PluginNotFoundException;
import net.orekyuu.javatter.api.plugin.PluginService;
import net.orekyuu.javatter.api.storage.DataStorageService;
import net.orekyuu.javatter.api.util.lookup.Lookup;

import javax.xml.bind.JAXB;
import java.io.*;
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

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return JAXB.unmarshal(reader, dataClass);
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
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return JAXB.unmarshal(reader, dataClass);
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
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return JAXB.unmarshal(reader, dataClass);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void save(String pluginID, Object obj) {
        Path path = getFilePath(pluginID, obj.getClass());
        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        try (BufferedWriter reader = Files.newBufferedWriter(path)) {
            JAXB.marshal(obj, reader);
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
