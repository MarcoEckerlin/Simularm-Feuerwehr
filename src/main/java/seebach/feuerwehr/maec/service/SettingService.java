package seebach.feuerwehr.maec.service;

import org.springframework.stereotype.Service;
import seebach.feuerwehr.maec.obj.Setting;
import seebach.feuerwehr.maec.repository.SettingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SettingService {

    private final SettingRepository repository;

    public SettingService(SettingRepository repository) {
        this.repository = repository;
    }

    public Setting saveSetting(String key, String value) {
        Optional<Setting> existing = repository.findBySetting(key);
        Setting setting = existing.orElse(new Setting());
        setting.setSetting(key);
        setting.setValue(value);
        return repository.save(setting);
    }

    public String getValue(String key) {
        return repository.findBySetting(key).map(Setting::getValue).orElse(null);
    }

    public List<Setting> getAllSettings() {
        return repository.findAll();
    }
}

