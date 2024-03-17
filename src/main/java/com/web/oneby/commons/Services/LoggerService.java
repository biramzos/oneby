package com.web.oneby.commons.Services;

import com.web.oneby.commons.Models.Logger;
import com.web.oneby.commons.Repositories.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {
    private LoggerRepository loggerRepository;

    @Autowired
    public LoggerService (LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    public void create(Logger logger){
        this.loggerRepository.save(logger);
    }
}
