package net.orekyuu.example;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import net.orekyuu.javatter.api.storage.DataStorageService;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class SettingsController implements Initializable {

    public CheckBox checkbox;
    @Inject
    private DataStorageService storageService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SettingsData data = storageService.find(ExamplePlugin.PLUGIN_ID, SettingsData.class, (Supplier<SettingsData>) SettingsData::new);
        checkbox.setSelected(data.isCheckBoxSelected());
        checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            data.setCheckBoxSelected(newValue);
            storageService.save(ExamplePlugin.PLUGIN_ID, data);
        });
    }
}
