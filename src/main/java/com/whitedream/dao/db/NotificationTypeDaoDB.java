package com.whitedream.dao.db;

import com.whitedream.dao.NotificationDao;
import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.Notification;
import com.whitedream.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class NotificationTypeDaoDB implements NotificationDao {
    private static final Logger logger = Logger.getLogger(NotificationTypeDaoDB.class);
    private static final String GET_NOTIFICATION_BY_ID = "SELECT * FROM notification_types WHERE notification_type_id = ?";
    private static final String GET_NOTIFICATION_BY_NAME = "SELECT * FROM notification_types WHERE notification_type_name = ?";
    private Connection connection;

    public NotificationTypeDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Notification getNotification(int id) throws NoEntityExistsException {
        return null;
    }

    @Override
    public List<String> getAllNotificationTypes() {
        return null;
    }

    @Override
    public List<Notification> getNotificationsByAddress(String destinationAddress) {
        return null;
    }

    @Override
    public List<Notification> getNotificationsByUser(User user) {
        return null;
    }

    @Override
    public List<Notification> getNotificationsByType(String notificationType) {
        return null;
    }

    @Override
    public Notification createNotification(Notification notification) throws EntityAlreadyExistsException {
        return null;
    }

    @Override
    public void changeNotificationType(Notification notification, String newType) throws NoEntityExistsException {

    }

    @Override
    public void changeNotificationAddress(Notification notification, String newDestinationAddress) throws NoEntityExistsException {

    }

    @Override
    public void deleteNotification(Notification notification) throws NoEntityExistsException {

    }

    @Override
    public List<Notification> getAllNotifications() {
        return null;
    }
}
