package net.orekyuu.javatter.core.settings.storage;

import net.orekyuu.javatter.api.service.Service;

//非公開API
@Service
public interface SettingsStorage {

    GeneralSetting getGeneralSetting();

    void saveGeneralSetting(GeneralSetting setting);
}
