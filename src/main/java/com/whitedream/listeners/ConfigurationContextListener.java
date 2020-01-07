package com.whitedream.listeners;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class ConfigurationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //Конфигурирование логгера
        ServletContext context = servletContextEvent.getServletContext();
        String loggerPropertiesPath = context.getInitParameter("loggerPropertiesPath");
        String fullPath = context.getRealPath("") + File.separator + loggerPropertiesPath;
        PropertyConfigurator.configure(fullPath);
        Logger logger = Logger.getLogger(ConfigurationContextListener.class);
        logger.info("Context initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
