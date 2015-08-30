package net.orekyuu.javatter.core.settings;

import com.gs.collections.api.list.ImmutableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.plugin.PluginInfo;
import net.orekyuu.javatter.api.plugin.PluginService;
import net.orekyuu.javatter.core.service.PluginServiceImpl;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    public TreeView<SettingsPage> treeView;
    public SplitPane root;
    @Inject
    private PluginService pluginService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<SettingsPage> rootItem = new TreeItem<>(null);
        TreeItem<SettingsPage> general = new TreeItem<>(new SettingsPage("一般"));
        general.getChildren().addAll(
                new TreeItem<>(new SettingsPage("/layout/settings/general.fxml", "一般設定")),
                new TreeItem<>(new SettingsPage("/layout/settings/account.fxml", "アカウント"))
        );
        TreeItem<SettingsPage> plugin = new TreeItem<>(new SettingsPage("プラグイン"));
        //ロードされているプラグインを追加
        ImmutableList<PluginInfo> allPluginInfo = pluginService.getAllPluginInfo();
        for (PluginInfo info : allPluginInfo) {
            //ビルドインは表示したくないのでスキップ
            if (info.getPluginId().equals(PluginServiceImpl.BUILD_IN.getPluginId())) {
                continue;
            }

            String page = info.getPluginPage();
            SettingsPage pluginPage;

            //カスタムページが設定されていなければ空のページを作成
            if (page == null) {
                pluginPage = new SettingsPage(info.getPluginName());
            } else {
                pluginPage = new SettingsPage(page, info.getPluginName(), info.getMain());
            }
            plugin.getChildren().add(new TreeItem<>(pluginPage));
        }

        rootItem.getChildren().addAll(general, plugin);
        treeView.setRoot(rootItem);

        treeView.setCellFactory(param -> new SettingsPageTreeCell());

    }

    private final class SettingsPageTreeCell extends TreeCell<SettingsPage> {

        @Override
        protected void updateItem(SettingsPage item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
            } else {
                setText(item.getTitle());
            }
        }

        @Override
        public void updateSelected(boolean selected) {
            super.updateSelected(selected);
            if (selected) {
                SettingsPage item = getItem();
                if (item != null && !item.isDummy()) {
                    URL url = null;
                    //MainClassがあればそのクラスからFXMLのURLを取得
                    if (item.getMainClass() == null) {
                        url = getClass().getResource(item.getFxmlURL());
                    } else {
                        try {
                            Class<?> aClass = pluginService.getPluginClassLoader().loadClass(item.getMainClass());
                            url = aClass.getResource(item.getFxmlURL());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    JavatterFXMLLoader loader = new JavatterFXMLLoader(url);
                    //MainClassが指定されていればプラグインからの読み込みなのでPluginClassLoaderを使用する
                    if (item.getMainClass() != null) {
                        loader.setClassLoader(pluginService.getPluginClassLoader());
                    }
                    try {

                        Node load = loader.load();
                        if (load != null) {
                            root.getItems().set(1, load);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //選択されるページなし
                }
            }
        }
    }
}
