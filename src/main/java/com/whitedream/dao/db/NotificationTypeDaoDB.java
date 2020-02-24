package com.whitedream.dao.db;

import com.whitedream.dao.NotificationTypeDao;
import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.NotificationType;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationTypeDaoDB implements NotificationTypeDao {
    private static final Logger logger = Logger.getLogger(NotificationTypeDaoDB.class);
    private static final String GET_ALL_NOTIFICATION_TYPES = "SELECT * FROM notification_types";
    private static final String GET_NOTIFICATION_BY_ID = "SELECT * FROM notification_types WHERE notification_type_id = ?";
    private static final String GET_NOTIFICATION_BY_NAME = "SELECT * FROM notification_types WHERE notification_type_name = ?";
    private static final String CREATE_NOTIFICATION_TYPE = "INSERT INTO notification_types (notification_type_name) VALUES (?)";
    private static final String CHANGE_NOTIFICATION_TYPE_NAME = "UPDATE notification_types SET notification_type_name = ? WHERE notification_type_name = ?";
    private static final String DELETE_NOTIFICATION_TYPE = "DELETE FROM notification_types WHERE notification_type_name = ?";

    private Connection connection;

    public NotificationTypeDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<NotificationType> getAllNotificationTypes() {
        List<NotificationType> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_NOTIFICATION_TYPES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("notification_type_id");
                String name = resultSet.getString("notification_type_name1");
                list.add(new NotificationType(id, name));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    @Override
    public NotificationType getNotificationType(int id) throws NoEntityExistsException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_NOTIFICATION_BY_ID);
            preparedStatement.setInt(1, id);
            return getNotificationType(preparedStatement, id);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public NotificationType getNotificationType(String name) throws NoEntityExistsException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_NOTIFICATION_BY_NAME);
            preparedStatement.setString(1, name);
            return getNotificationType(preparedStatement, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private NotificationType getNotificationType(PreparedStatement ps, Object value) throws SQLException, NoEntityExistsException {
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("notification_type_id");
            String name = resultSet.getString("notification_type_name");
            NotificationType notificationType = new NotificationType(id, name);
            logger.debug("Успешно запрошен тип уведомления из БД: " + notificationType);
            return notificationType;
        } else {
            logger.error("Запрошенный тип уведомления не найден по значению: " + value);
            throw new NoEntityExistsException("Запрошенный тип уведомления не найден");
        }
    }

    @Override
    public NotificationType createNotificationType(NotificationType notificationType) throws EntityAlreadyExistsException {
        if (notificationType == null) throw new IllegalArgumentException("Тип уведомления не может быть null");
        try {
            if (isNotificationTypeExist(notificationType.getName()))
                throw new EntityAlreadyExistsException("Указанный тип уведомления уже существует в БД");
            PreparedStatement ps = connection.prepareStatement(CREATE_NOTIFICATION_TYPE);
            ps.setObject(1, notificationType.getName());
            int numberOfUpdated = ps.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    notificationType.setId((int) generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Не присвоен ID в БД для уведомления");
                }
            }
            logger.debug(String.format("Успешно создана роль '%s'. Обновлено строк %d", notificationType.getName(), numberOfUpdated));
            return notificationType;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void changeNotificationTypeName(NotificationType notificationType, String newName) throws NoEntityExistsException {
        if (notificationType == null || newName == null)
            throw new IllegalArgumentException("Тип уведомления или или новое имя типа не может быть null");
        try {
            if (!isNotificationTypeExist(notificationType.getName()))
                throw new NoEntityExistsException("Указанного типа уведомления не существует в БД");
            PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_NOTIFICATION_TYPE_NAME);
            preparedStatement.setObject(1, newName);
            preparedStatement.setObject(2, notificationType.getName());
            int numberOfUpdated = preparedStatement.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
            logger.debug(String.format("Успешно изменен тип уведомления '%s'. Изменено название на '%s'. Обновлено строк %d", notificationType.getName(), newName, numberOfUpdated));
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void deleteNotificationType(NotificationType notificationType) throws NoEntityExistsException {
        if (notificationType == null) throw new IllegalArgumentException("Тип уведомления не может быть null");
        try {
            if (!isNotificationTypeExist(notificationType.getName()))
                throw new NoEntityExistsException("Указанного типа уведомления не существует в БД");
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NOTIFICATION_TYPE);
            preparedStatement.setObject(1, notificationType.getName());
            int numberOfUpdated = preparedStatement.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
            logger.debug(String.format("Успешно удалён тип уведомления '%s'. Обновлено строк %d", notificationType.getName(), numberOfUpdated));
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    //проверка наличия типа уведомленяи в БД
    private boolean isNotificationTypeExist(String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_NOTIFICATION_BY_NAME);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
