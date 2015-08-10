package net.orekyuu.javatter.core.settings;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import net.orekyuu.javatter.api.controller.JavatterFXMLLoader;
import net.orekyuu.javatter.api.settings.SettingsPage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    public TreeView<SettingsPage> treeView;
    public SplitPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<SettingsPage> rootItem = new TreeItem<>(null);
        TreeItem<SettingsPage> general = new TreeItem<>(new SettingsPage("一般"));
        general.getChildren().addAll(
                new TreeItem<>(new SettingsPage("/layout/settings/general.fxml", "一般設定")),
                new TreeItem<>(new SettingsPage("/layout/settings/account.fxml", "アカウント"))
        );
        TreeItem<SettingsPage> plugin = new TreeItem<>(new SettingsPage("プラグイン"));
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
                    JavatterFXMLLoader loader = new JavatterFXMLLoader(getClass()
                            .getResource(item.getFxmlURL()));
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
