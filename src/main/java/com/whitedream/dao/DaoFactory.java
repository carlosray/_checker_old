package com.whitedream.dao;

import com.whitedream.dao.db.NotificationDaoDB;
import com.whitedream.dao.db.NotificationTypeDaoDB;
import com.whitedream.dao.db.RoleDaoDB;
import com.whitedream.dao.db.UserDaoDB;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory {

    public static NotificationDao getNotificationDao(Connection connection) throws SQLException {
        return new NotificationDaoDB(connection);
    }

    public static NotificationTypeDao getNotificationTypeDao(Connection connection) throws SQLException {
        return new NotificationTypeDaoDB(connection);
    }

    public static RoleDao getRoleDao(Connection connection) throws SQLException {
        return new RoleDaoDB(connection);
    }

    public static UserDao getUserDao(Connection connection) throws SQLException {
        return new UserDaoDB(connection);
    }
}
