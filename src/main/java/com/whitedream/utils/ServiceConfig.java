package com.whitedream.utils;

import org.apache.log4j.Logger;

import java.util.Properties;

public class ServiceConfig {
    private static final Logger logger = Logger.getLogger(ServiceConfig.class);
    private static Properties CONFIG;

    public static Properties getConfig(){
        if (CONFIG == null){
            logger.error("Попытка вернуть конфиг, который не был загружен");
            throw new NullPointerException("Конфиг не был загружен");
        }
        return CONFIG;
    }

    public static void setConfig(Properties properties){
        CONFIG = properties;
    }
}
