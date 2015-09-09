package net.orekyuu.javatter.core.service;

import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.impl.factory.Lists;
import net.orekyuu.javatter.api.column.ColumnState;
import net.orekyuu.javatter.api.column.ColumnStateStorageService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColumnStateStorageServiceImpl implements ColumnStateStorageService {

    private static final Logger logger = Logger.getLogger(ColumnStateStorageServiceImpl.class.getName());
    private Path path = Paths.get("column.state");


    @Override
    public void save(ImmutableList<ColumnState> columnStates) {
        logger.log(Level.INFO, columnStates.toString());
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            outputStream.writeObject(columnStates);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ImmutableList<ColumnState> loadColumnStates() {
        logger.log(Level.INFO, "load");

        if (Files.notExists(path)) {
            return Lists.immutable.of();
        }

        try (ObjectInputStream outputStream = new ObjectInputStream(Files.newInputStream(path))) {
            return (ImmutableList<ColumnState>) outputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Lists.immutable.of();
    }
}
