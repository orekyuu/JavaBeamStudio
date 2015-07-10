package net.orekyuu.javatter.core.service;

import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.impl.factory.Lists;
import com.gs.collections.impl.factory.Maps;
import javafx.scene.Node;
import net.orekyuu.javatter.api.column.Column;
import net.orekyuu.javatter.api.column.ColumnController;
import net.orekyuu.javatter.api.column.ColumnFactory;
import net.orekyuu.javatter.api.column.ColumnState;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.service.ColumnManager;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class ColumnManagerImpl implements ColumnManager {

    private MutableMap<String, String> factories;
    private MutableMap<String, String> names;

    public ColumnManagerImpl() {
        MutableMap<String, String> map = Maps.mutable.of();
        factories = map.asSynchronized();
        names = Maps.mutable.of();
        names = names.asSynchronized();
    }

    @Override
    public void registerColumn(String pluginId, String columnName, String fxmlPath, String name) {
        String key = pluginId + ":" + columnName;
        factories.put(key, fxmlPath);
        names.put(key, name);
    }

    @Override
    public void unregisterColumn(String pluginId, String columnName) {
        factories.remove(pluginId + ":" + columnName);
    }

    @Override
    public ImmutableList<ColumnInfo> getAllColumnInfo() {
        MutableList<ColumnInfo> result = Lists.mutable.of();
        for (Map.Entry<String, String> entry : names.entrySet()) {
            String[] strings = entry.getKey().split(":");
            result.add(new ColumnInfo(strings[0], strings[1], entry.getValue()));
        }
        return result.toImmutable();
    }

    @Override
    public Optional<ColumnFactory> findByPluginIdAndColumnId(String pluginId, String columnId) {
        ColumnFactory factory = new ColumnFactory() {
            @Override
            public Column newInstance(ColumnState state) throws IOException {
                String fxml = factories.get(pluginId + ":" + columnId);
                JavatterFXMLLoader loader = new JavatterFXMLLoader(getClass().getResource(fxml));
                Node node = loader.load();
                ColumnController columnController = loader.getController();
                columnController.restoration(state);

                return new Column(columnController, node);
            }
        };
        return Optional.ofNullable(factory);
    }

    @Override
    public ImmutableList<ColumnFactory> findAll() {
        //ラムダだとネスト深すぎてわからなくなったので匿名クラス
        MutableList<ColumnFactory> collect = factories.toList().collect(new Function<String, ColumnFactory>() {
            @Override
            public ColumnFactory valueOf(String fxmlPath) {
                return new ColumnFactory() {
                    @Override
                    public Column newInstance(ColumnState state) throws IOException {
                        JavatterFXMLLoader fxmlLoader = new JavatterFXMLLoader(getClass().getResource(fxmlPath));
                        Node load = fxmlLoader.load();
                        ColumnController columnController = fxmlLoader.getController();
                        columnController.restoration(state);
                        return new Column(columnController, load);
                    }
                };
            }
        });

        return collect.toImmutable();

    }
}
