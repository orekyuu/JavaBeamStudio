package net.orekyuu.javatter.core.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import net.orekyuu.javatter.core.settings.storage.GeneralSetting;
import net.orekyuu.javatter.core.settings.storage.NameViewType;
import net.orekyuu.javatter.core.settings.storage.SettingsStorage;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralSettingsPageController implements Initializable {

    public CheckBox tweetCheck;
    public CheckBox rtCheck;
    public CheckBox favCheck;
    public ComboBox<NameViewType> nameViewType;
    @Inject
    private SettingsStorage settingsStorage;

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

        GeneralSetting setting = settingsStorage.getGeneralSetting();
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
            settingsStorage.saveGeneralSetting(temp);
        });
        rtCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            temp.setCheckRT(newValue);
            settingsStorage.saveGeneralSetting(temp);
        });
        favCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
            temp.setCheckFavorite(newValue);
            settingsStorage.saveGeneralSetting(temp);
        });
        nameViewType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            temp.setNameViewType(newValue.name());
            settingsStorage.saveGeneralSetting(temp);
        });
    }
}
