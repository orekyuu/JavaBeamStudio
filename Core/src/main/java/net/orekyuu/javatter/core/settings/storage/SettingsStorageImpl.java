package net.orekyuu.javatter.core.settings.storage;

import javax.xml.bind.JAXB;
import java.io.File;
import java.io.IOException;

public class SettingsStorageImpl implements SettingsStorage {

    @Override
    public GeneralSetting getGeneralSetting() {
        try {
            GeneralSetting setting = JAXB.unmarshal(getGeneralSettingsFile(), GeneralSetting.class);
            if (setting == null) {
                setting = new GeneralSetting();
            }
            return setting;
        } catch (Exception e) {
            saveGeneralSetting(new GeneralSetting());
        }
        return new GeneralSetting();
    }

    @Override
    public void saveGeneralSetting(GeneralSetting setting) {
        JAXB.marshal(setting, getGeneralSettingsFile());
    }

    private File getGeneralSettingsFile() {
        File file = new File("general-settings.xml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
