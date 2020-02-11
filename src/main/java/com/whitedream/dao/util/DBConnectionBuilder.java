package com.whitedream.dao.util;

import com.whitedream.utils.ServiceConfig;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionBuilder implements ConnectionBuilder{
    private static final Logger logger = Logger.getLogger(DBConnectionBuilder.class);
    private static final Properties PROPERTIES = ServiceConfig.getConfig();
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        String driver = PROPERTIES.getProperty("db.driver");
        URL = PROPERTIES.getProperty("db.url");
        USER = PROPERTIES.getProperty("db.login");
        PASSWORD = PROPERTIES.getProperty("db.password");
        try {
            Class.forName(driver);
            logger.debug("Драйвер (JDBC) загружен: " + driver);
        } catch (ClassNotFoundException e) {
            logger.error("Driver class not found", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
