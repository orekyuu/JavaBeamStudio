package net.orekyuu.javatter.core.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import net.orekyuu.javatter.core.settings.storage.GeneralSetting;
import net.orekyuu.javatter.core.settings.storage.SettingsStorage;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralSettingsPageController implements Initializable {

    public CheckBox tweetCheck;
    public CheckBox rtCheck;
    public CheckBox favCheck;
    @Inject
    private SettingsStorage settingsStorage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GeneralSetting setting = settingsStorage.getGeneralSetting();
        if (setting == null) {
            setting = new GeneralSetting();
        }
        tweetCheck.setSelected(setting.isCheckTweet());
        rtCheck.setSelected(setting.isCheckRT());
        favCheck.setSelected(setting.isCheckFavorite());

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
    }
}
