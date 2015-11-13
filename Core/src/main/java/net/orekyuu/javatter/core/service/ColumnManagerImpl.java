package net.orekyuu.javatter.core.service;

import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.list.ImmutableList;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.impl.factory.Lists;
import com.gs.collections.impl.factory.Maps;
import javafx.scene.Node;
import net.orekyuu.javatter.api.column.*;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.plugin.PluginService;
import net.orekyuu.javatter.api.util.lookup.Lookup;

import java.io.IOException;
import java.net.URL;
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
        String fxml = factories.get(pluginId + ":" + columnId);
        if (fxml == null) {
            return Optional.empty();
        }
        ColumnFactory factory = state -> {

            //PluginServiceからPluginClassLoaderを取得
            PluginService service = Lookup.lookup(PluginService.class);
            ClassLoader classLoader = service.getPluginClassLoader();
            //ClassLoaderからfxmlのパスを取得
            URL resource = classLoader.getResource(fxml);

            JavatterFXMLLoader loader = new JavatterFXMLLoader(resource);
            //コントローラが作成できるようにPluginClassLoaderを設定
            loader.setClassLoader(classLoader);

            Node node = loader.load();
            ColumnController columnController = loader.getController();
            if (state == null) {
                state = columnController.defaultColumnState();
            }
            columnController.restoration(state);

            return new Column(columnController, node);
        };
        return Optional.of(factory);
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
                        if (state == null) {
                            state = columnController.defaultColumnState();
                        }
                        columnController.restoration(state);
                        return new Column(columnController, load);
                    }
                };
            }
        });

        return collect.toImmutable();

    }
}
