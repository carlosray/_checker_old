package com.whitedream.listeners;

import com.whitedream.dao.util.ConnectionBuilder;
import com.whitedream.dao.util.DBConnectionBuilder;
import com.whitedream.utils.ServiceConfig;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ConfigurationContextListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ConfigurationContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        //Конфигурирование логгера
        String loggerPropertiesPath = context.getInitParameter("loggerProperties");
        logger.info("\n<--------------------------------------------------------------------------->" +
                    "\n<------------------------------SERVER STARTED------------------------------->" +
                    "\n<--------------------------------------------------------------------------->");
        loadResourceFile(loggerPropertiesPath);
        logger.debug("Логгер сконфигурирован");
        //загрузка конфига проекта
        String projectPropertiesPath = context.getInitParameter("projectProperties");
        loadResourceFile(projectPropertiesPath);
        //инициализация соединения с базой
        ConnectionBuilder connectionBuilder = new DBConnectionBuilder();
        try (Connection connection = connectionBuilder.getConnection()){
            logger.debug("Соединение с базой установлено");
        } catch (SQLException e) {
            logger.error("Ошибка при соединении с базой: " + e.getMessage());
        }
        logger.info("_CONTEXT INITIALIZED_\n");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("\n<--------------------------------------------------------------------------->" +
                    "\n<------------------------------SERVER STOPPED------------------------------->" +
                    "\n<--------------------------------------------------------------------------->");
    }

    private void loadResourceFile(String resourceName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            Properties properties = new Properties();
            if (is == null) throw new IOException("Файл конфига не найден: '" + resourceName + "'");
            properties.load(is);
            ServiceConfig.setConfig(properties);
            logger.debug("Конфиг загружен: \"" + resourceName + "\"");
        } catch (IOException e) {
            logger.error("Ошибка при загрузке конфига: " + e.getMessage());
        }
    }
}
