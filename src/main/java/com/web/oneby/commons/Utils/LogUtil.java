package com.web.oneby.commons.Utils;


import com.web.oneby.commons.Enums.LogType;
import com.web.oneby.commons.Models.Logger;
import com.web.oneby.commons.Services.LoggerService;
import com.web.oneby.modules.users.Models.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class LogUtil {

    private static LoggerService loggerService;

    @Autowired
    public LogUtil (LoggerService loggerService) {
        LogUtil.loggerService = loggerService;
    }

    public static void write(String message, LogType type) {
        Long userId = 0L;
        if (!SecurityContextHolder.getContext().getAuthentication().getCredentials().equals("")) {
            userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        }
        loggerService.create(new Logger(type, message, userId));
        if (type == LogType.INFO) {
            log.info(message);
        } else if (type == LogType.WARNING) {
            log.warn(message);
        } else if (type == LogType.ERROR){
            log.error(message);
        }
    }
}
