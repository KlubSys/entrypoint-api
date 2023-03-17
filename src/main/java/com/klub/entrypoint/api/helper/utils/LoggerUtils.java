package com.klub.entrypoint.api.helper.utils;

import java.util.logging.Logger;

public class LoggerUtils {
    public static Logger log = Logger.getLogger("LoggerUtils");

    public static void info(String message){
        log.info(message);
    }

    public static void err(String message, Class<?> clazz){
        log.severe(String.format("[Class: %s] %s", clazz.getName(), message));
    }
}
