package com.web.oneby.Services;

import com.web.oneby.Repositories.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService {
    private SettingRepository settingRepository;

    @Autowired
    public SettingService (
            SettingRepository settingRepository
    ) {
        this.settingRepository = settingRepository;
    }
}
