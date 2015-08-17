package net.orekyuu.javatter.core.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import net.orekyuu.javatter.api.storage.DataStorageService;
import net.orekyuu.javatter.core.service.PluginServiceImpl;
import net.orekyuu.javatter.core.settings.storage.GeneralSetting;
import net.orekyuu.javatter.core.settings.storage.NameViewType;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralSettingsPageController implements Initializable {

    public CheckBox tweetCheck;
    public CheckBox rtCheck;
    public CheckBox favCheck;
    public ComboBox<NameViewType> nameViewType;
    @Inject
    private DataStorageService storageService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameViewType.getItems().addAll(NameViewType.values());
        nameViewType.setCellFactory(new Callback<ListView<NameViewType>, ListCell<NameViewType>>() {
            @Override
            public ListCell<NameViewType> call(ListView<NameViewType> param) {
                return new ListCell<NameViewType>() {
                    @Override
                    protected void updateItem(NameViewType item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item.getView());
                        }
                    }
                };
            }
        });

        GeneralSetting setting = storageService.find(PluginServiceImpl.BUILD_IN.getPluginId(), GeneralSetting.class, new GeneralSetting());
        if (setting == null) {
            setting = new GeneralSetting();
        }
        tweetCheck.setSelected(setting.isCheckTweet());
        rtCheck.setSelected(setting.isCheckRT());
        favCheck.setSelected(setting.isCheckFavorite());
        nameViewType.getSelectionModel().select(NameViewType.valueOf(setting.getNameViewType()));

        GeneralSetting temp = setting;
        tweetCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            temp.setCheckTweet(newValue);
            storageService.save(PluginServiceImpl.BUILD_IN.getPluginId(), temp);
        });
        rtCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            temp.setCheckRT(newValue);
            storageService.save(PluginServiceImpl.BUILD_IN.getPluginId(), temp);
        });
        favCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            temp.setCheckFavorite(newValue);
            storageService.save(PluginServiceImpl.BUILD_IN.getPluginId(), temp);
        });
        nameViewType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            temp.setNameViewType(newValue.name());
            storageService.save(PluginServiceImpl.BUILD_IN.getPluginId(), temp);
        });
    }
}
