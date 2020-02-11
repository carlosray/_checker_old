package com.whitedream.dao.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestDBConnectionBuilder implements ConnectionBuilder {
    private final String URL;
    private final String USER;
    private final String PASSWORD;

    public TestDBConnectionBuilder() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("test.properties");
        Properties properties = new Properties();
        properties.load(is);
        String driver = properties.getProperty("test.db.driver");
        URL = properties.getProperty("test.db.url");
        USER = properties.getProperty("test.db.login");
        PASSWORD = properties.getProperty("test.db.password");
        try {
            Class.forName(driver);
            System.out.println(("Драйвер (JDBC) загружен: " + driver));
        } catch (ClassNotFoundException e) {
            System.out.println("Driver class not found" + e.getMessage());
        }
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
