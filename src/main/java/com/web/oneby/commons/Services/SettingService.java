package com.web.oneby.commons.Services;

import com.web.oneby.commons.Repositories.SettingRepository;
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
