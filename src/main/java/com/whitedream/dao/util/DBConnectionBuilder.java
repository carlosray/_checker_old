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
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private static final String PREFIX = "lite.";

    public DBConnectionBuilder() {
        String driver = PROPERTIES.getProperty(PREFIX + "db.driver");
        URL = PROPERTIES.getProperty(PREFIX + "db.url");
        USER = PROPERTIES.getProperty(PREFIX + "db.login");
        PASSWORD = PROPERTIES.getProperty(PREFIX + "db.password");
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
