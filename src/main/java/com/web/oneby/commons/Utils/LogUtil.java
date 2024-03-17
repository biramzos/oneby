package com.web.oneby.commons.Utils;


import com.web.oneby.commons.Enums.LogType;
import com.web.oneby.commons.Models.Logger;
import com.web.oneby.commons.Services.LoggerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtil {

    private static LoggerService loggerService;
    public static void write(String message, LogType type) {
        loggerService.create(new Logger(type, message));
        if (type == LogType.SUCCESS) {
            log.debug(message);
        } else if (type == LogType.INFO) {
            log.info(message);
        } else if (type == LogType.WARNING) {
            log.warn(message);
        } else if (type == LogType.ERROR){
            log.error(message);
        }
    }
}
