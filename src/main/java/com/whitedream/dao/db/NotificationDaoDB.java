package com.whitedream.dao.db;

import com.whitedream.dao.DaoFactory;
import com.whitedream.dao.NotificationDao;
import com.whitedream.dao.UserDao;
import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.Notification;
import com.whitedream.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationDaoDB implements NotificationDao {
    private static final Logger logger = Logger.getLogger(NotificationDaoDB.class);
    private static final String GET_NOTIFICATION_BY_ID = "SELECT * FROM notifications " +
            "JOIN notification_types ON notification_types.notification_type_id = notifications.notification_type_id " +
            "WHERE notifications.notification_id = ?";
    private static final String GET_NOTIFICATION_BY_ADDRESS = "SELECT * FROM notifications " +
            "JOIN notification_types ON notification_types.notification_type_id = notifications.notification_type_id " +
            "WHERE notifications.destination_address = ?";
    private static final String GET_NOTIFICATION_BY_TYPE = "SELECT * FROM notifications " +
            "JOIN notification_types ON notification_types.notification_type_id = notifications.notification_type_id " +
            "WHERE notification_types.notification_type_name = ?";
    private static final String GET_NOTIFICATION_BY_USER = "SELECT * FROM notifications " +
            "JOIN notification_types ON notification_types.notification_type_id = notifications.notification_type_id " +
            "WHERE notifications.user_id = ?";
    private static final String GET_ALL_NOTIFICATIONS = "SELECT * FROM notifications " +
            "JOIN notification_types ON notification_types.notification_type_id = notifications.notification_type_id ";
    private static final String GET_NOTIFICATION_TYPES = "SELECT * FROM notification_types";
    private static final String CREATE_NOTIFICATION = "INSERT INTO notifications (notification_type_id, user_id, destination_address, created_at) VALUES (?,?,?,?)";
    private static final String GET_NOTIFICATION_TYPE_ID_BY_NAME = "SELECT notification_type_id FROM notification_types WHERE notification_type_name = ?";
    private static final String UPDATE_NOTIFICATION = "UPDATE notifications SET ? = ? WHERE notification_type_id = ? and user_id = ? and destination_address = ?";
    private static final String DELETE_NOTIFICATION = "DELETE FROM notifications WHERE notification_type_id = ? and user_id = ? and destination_address = ?";
    private Connection connection;

    public NotificationDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Notification getNotification(int id) throws NoEntityExistsException {
        try {
            PreparedStatement ps = connection.prepareStatement(GET_NOTIFICATION_BY_ID);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String type = resultSet.getString("notification_type_name");
                String destAddress = resultSet.getString("destination_address");
                int userId = resultSet.getInt("user_id");
                User user = DaoFactory.getUserDao(connection).getUser(userId);
                Date date = resultSet.getDate("created_at");
                Notification notification = new Notification(, destAddress, user, date);
                logger.debug("Успешно запрошено уведомление (notification) из БД по ID: " + id);
                return notification;
            } else {
                logger.error("Запрошенное уведомление (notification) не найдено по значению: " + id);
                throw new NoEntityExistsException("Запрошенное уведомление (notification) не найдено");
            }
        } catch (SQLException e) {
            logger.error("Ошибка при получении уведомления (notification): " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<String> getAllNotificationTypes() {
        List<String> types = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_NOTIFICATION_TYPES);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String type = resultSet.getString("notification_type_name");
                types.add(type);
            }
            logger.debug("Успешно запрошен список всех типов уведомлений (notification) ");
        } catch (SQLException e) {
            logger.error("Ошибка при получении списка всех типов уведомлений (notification): " + e.getMessage());
        }
        return types;
    }

    @Override
    public List<Notification> getNotificationsByAddress(String destinationAddress) {
        if (destinationAddress == null) throw new IllegalArgumentException("Адрес назначения (destinationAddress) не может быть null");
        List<Notification> notifications = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_NOTIFICATION_BY_ADDRESS);
            ps.setObject(1, destinationAddress);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String type = resultSet.getString("notification_type_name");
                int userId = resultSet.getInt("user_id");
                User user = DaoFactory.getUserDao(connection).getUser(userId);
                Date date = resultSet.getDate("created_at");
                Notification notification = new Notification(type, destinationAddress, user, date);
                notifications.add(notification);
            }
            logger.debug("Успешно запрошен список всех уведомлений (notification) по адресу: " + destinationAddress);
        } catch (SQLException | NoEntityExistsException e) {
            logger.error("Ошибка при получении списка всех уведомлений (notification) по адресу '" + destinationAddress + "' : " + e.getMessage());
        }
        return notifications;
    }

    @Override
    public List<Notification> getNotificationsByUser(User user) {
        if (user == null) throw new IllegalArgumentException("Пользователь не может быть null");
        List<Notification> notifications = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_NOTIFICATION_BY_USER);
            ps.setInt(1, user.getId());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String type = resultSet.getString("notification_type_name");
                User userDB = DaoFactory.getUserDao(connection).getUser(user.getId());
                Date date = resultSet.getDate("created_at");
                String destinationAddress = resultSet.getString("destination_address");
                Notification notification = new Notification(type, destinationAddress, userDB, date);
                notifications.add(notification);
            }
            logger.debug("Успешно запрошен список всех уведомлений (notification) пользователя: " + user.getUserName());
        } catch (SQLException | NoEntityExistsException e) {
            logger.error("Ошибка при получении списка всех уведомлений (notification) пользователя '" + user.getUserName() + "' : " + e.getMessage());
        }
        return notifications;
    }

    @Override
    public List<Notification> getNotificationsByType(String notificationType) {
        if (notificationType == null) throw new IllegalArgumentException("Тип уведомления (notificationType) не может быть null");
        List<Notification> notifications = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_NOTIFICATION_BY_TYPE);
            ps.setObject(1, notificationType);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                User user = DaoFactory.getUserDao(connection).getUser(userId);
                Date date = resultSet.getDate("created_at");
                String destinationAddress = resultSet.getString("destination_address");
                Notification notification = new Notification(notificationType, destinationAddress, user, date);
                notifications.add(notification);
            }
            logger.debug("Успешно запрошен список всех уведомлений (notification) по типу: " + notificationType);
        } catch (SQLException | NoEntityExistsException e) {
            logger.error("Ошибка при получении списка всех уведомлений (notification) по типу '" + notificationType + "' : " + e.getMessage());
        }
        return notifications;
    }

    @Override
    public Notification createNotification(Notification notification) throws EntityAlreadyExistsException {
        if (notification == null) throw new IllegalArgumentException("Уведомление (notification) не может быть null");
        try {
            if (isNotificationExist(notification)) {
                throw new EntityAlreadyExistsException("Данное уведомление уже есть в базе");
            }
            int typeId = getNotificationTypeId(notification.getType());
            if (typeId == -1) {
                throw new IllegalArgumentException("Не найден данный тип уведомления: " + notification.getType());
            }
            int userId = notification.getUser().getId();
            if (userId == 0) {
                UserDao dao = DaoFactory.getUserDao(connection);
                User user = dao.getUser(notification.getUser().getUserName());
                userId = user.getId();
                notification.getUser().setId(userId);
            }
            PreparedStatement ps = connection.prepareStatement(CREATE_NOTIFICATION);
            ps.setInt(1, typeId);
            ps.setInt(2, userId);
            ps.setObject(3, notification.getDestinationAddress());
            java.sql.Date creationDate = new java.sql.Date(new Date().getTime());
            ps.setDate(4, creationDate);
            int numberOfUpdated = ps.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    notification.setCreationDate(creationDate);
                } else {
                    throw new SQLException("Не присвоен ID в БД для уведомления");
                }
            }
            logger.debug("Успешно создано уведомление: " + notification);
            return notification;
        } catch (SQLException | NoEntityExistsException e) {
            logger.error("Ошибка при создании нового уведомления (notification) : " + notification + " : " + e.getMessage());
        }
        return null;
    }

    @Override
    public void changeNotificationType(Notification notification, String newType) throws NoEntityExistsException {
        if (notification == null || newType == null) throw new IllegalArgumentException("Уведомление (notification) или новый тип не может быть null");
        try {
            if (!isNotificationExist(notification)) {
                throw new NoEntityExistsException("Данное уведомление не найдено в базе");
            }
            int typeIdOld = getNotificationTypeId(notification.getType());
            if (typeIdOld == -1) {
                throw new IllegalArgumentException("Не найден данный тип уведомления: " + notification.getType());
            }
            int typeIdNew = getNotificationTypeId(newType);
            if (typeIdNew == -1) throw new IllegalArgumentException("Не найден данный тип уведомления: " + newType);
            int userId = notification.getUser().getId();
            if (userId == 0) {
                UserDao dao = DaoFactory.getUserDao(connection);
                User user = dao.getUser(notification.getUser().getUserName());
                userId = user.getId();
                notification.getUser().setId(userId);
            }
            PreparedStatement ps = connection.prepareStatement(UPDATE_NOTIFICATION);
            ps.setObject(1, "notification_type_id");
            ps.setInt(2, typeIdNew);
            ps.setInt(3, typeIdOld);
            ps.setInt(4, userId);
            ps.setObject(5, notification.getDestinationAddress());
            int numberOfUpdated = ps.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
            notification.setType(newType);
            logger.debug("Успешно изменен тип уведомления: " + notification);
        } catch (SQLException e) {
            logger.error("Ошибка при изменении типа уведомления (notification) : " + notification + ": Новый тип '" + newType + "' " + e.getMessage());
        }
    }

    @Override
    public void changeNotificationAddress(Notification notification, String newDestinationAddress) throws NoEntityExistsException {
        if (notification == null || newDestinationAddress == null) throw new IllegalArgumentException("Уведомление (notification) или адрес назначения (newDestinationAddress) не может быть null");
        try {
            if (!isNotificationExist(notification)) {
                throw new NoEntityExistsException("Данное уведомление не найдено в базе");
            }
            int typeId = getNotificationTypeId(notification.getType());
            if (typeId == -1) {
                throw new IllegalArgumentException("Не найден данный тип уведомления: " + notification.getType());
            }
            int userId = notification.getUser().getId();
            if (userId == 0) {
                UserDao dao = DaoFactory.getUserDao(connection);
                User user = dao.getUser(notification.getUser().getUserName());
                userId = user.getId();
                notification.getUser().setId(userId);
            }
            PreparedStatement ps = connection.prepareStatement(UPDATE_NOTIFICATION);
            ps.setObject(1, "destination_address");
            ps.setObject(2, newDestinationAddress);
            ps.setInt(3, typeId);
            ps.setInt(4, userId);
            ps.setObject(5, notification.getDestinationAddress());
            int numberOfUpdated = ps.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
            notification.setDestinationAddress(newDestinationAddress);
            logger.debug("Успешно изменен тип уведомления: " + notification);
        } catch (SQLException e) {
            logger.error("Ошибка при изменении адреса уведомления (notification) : " + notification + ": Новый адрес '" + newDestinationAddress + "'" + e.getMessage());
        }
    }

    @Override
    public void deleteNotification(Notification notification) throws NoEntityExistsException {
        if (notification == null) throw new IllegalArgumentException("Уведомление (notification) не может быть null");
        try {
            if (!isNotificationExist(notification)) {
                throw new NoEntityExistsException("Данное уведомление не найдено в базе");
            }
            int typeId = getNotificationTypeId(notification.getType());
            if (typeId == -1) {
                throw new IllegalArgumentException("Не найден данный тип уведомления: " + notification.getType());
            }
            int userId = notification.getUser().getId();
            if (userId == 0) {
                UserDao dao = DaoFactory.getUserDao(connection);
                User user = dao.getUser(notification.getUser().getUserName());
                userId = user.getId();
                notification.getUser().setId(userId);
            }
            PreparedStatement ps = connection.prepareStatement(DELETE_NOTIFICATION);
            ps.setInt(1, typeId);
            ps.setInt(2, userId);
            ps.setObject(3, notification.getDestinationAddress());
            int numberOfUpdated = ps.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
        } catch (SQLException e) {
            logger.error("Ошибка при удалении уведомления (notification) : " + notification + " : " + e.getMessage());
        }
    }

    @Override
    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_NOTIFICATIONS);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                User user = DaoFactory.getUserDao(connection).getUser(userId);
                Date date = resultSet.getDate("created_at");
                String destinationAddress = resultSet.getString("destination_address");
                String notificationType = resultSet.getString("notification_type_name");
                Notification notification = new Notification(notificationType, destinationAddress, user, date);
                notifications.add(notification);
            }
            logger.debug("Успешно запрошен список всех уведомлений (notification)");
        } catch (SQLException | NoEntityExistsException e) {
            logger.error("Ошибка при получении списка всех уведомлений (notification): " + e.getMessage());
        }
        return notifications;
    }

    //проверка наличия данного уведомления уже в БД
    private boolean isNotificationExist(Notification notification) {
        List<Notification> notifications = getNotificationsByUser(notification.getUser());
        return !notifications.isEmpty() && notifications.contains(notification);
    }

    //достать notification_type_id по названию типа уведомления, возвращает -1, если не найдено
    private int getNotificationTypeId(String notificationName) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(GET_NOTIFICATION_TYPE_ID_BY_NAME);
        ps.setObject(1, notificationName);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("notification_type_id");
        }
        return -1;
    }
}
