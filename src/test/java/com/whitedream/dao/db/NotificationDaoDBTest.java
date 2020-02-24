package com.whitedream.dao.db;

import com.whitedream.dao.NotificationDao;
import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.Notification;
import com.whitedream.model.NotificationType;
import com.whitedream.model.Role;
import com.whitedream.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class NotificationDaoDBTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private NotificationDao dao;
    private Notification notification;

    @Before
    public void setUp() throws Exception {
        Mockito.when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        dao = new NotificationDaoDB(connection);
        notification = new Notification(new NotificationType(1, "email"), "test@mail.ru", new User(1, "usera", "usera", new Role("admin"), new Date()));

    }

    @Test(expected=NoEntityExistsException.class)
    public void getNotificationNotExistsThrowException() throws SQLException, NoEntityExistsException {
        Mockito.when(resultSet.next()).thenReturn(false);
        dao.getNotification(1);
    }

    @Test
    public void getNotification() throws SQLException, NoEntityExistsException {
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true);
        Mockito.when(resultSet.getString("notification_type_name")).thenReturn("email");
        Mockito.when(resultSet.getString("destination_address")).thenReturn("test@mail.ru");
        Mockito.when(resultSet.getInt("user_id")).thenReturn(1).thenReturn(1);
        Mockito.when(resultSet.getString("user_name")).thenReturn("usera");
        Mockito.when(resultSet.getString("password")).thenReturn("usera");
        Mockito.when(resultSet.getInt("user_role_id")).thenReturn(1);
        Mockito.when(resultSet.getInt("role_id")).thenReturn(1);
        Mockito.when(resultSet.getString("role_name")).thenReturn("admin");
        Mockito.when(resultSet.getDate("created_at")).thenReturn(new java.sql.Date(notification.getUser().getCreationDate().getTime()));
        Notification notificationTest = dao.getNotification(1);
        Assert.assertEquals(notification, notificationTest);
        Notification notTestEmail = new Notification(new NotificationType(1, "email"), "other@mail.ru", new User(1, "usera", "usera", new Role("admin"), new Date()));
        Assert.assertNotEquals(notTestEmail, notificationTest);
        Notification notTestRole = new Notification(new NotificationType(1, "email"), "test@mail.ru", new User(1, "usera", "usera", new Role("member"), new Date()));
        Assert.assertNotEquals(notTestRole, notificationTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNotificationsByAddressNull() {
        dao.getNotificationsByAddress(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNotificationNull() throws EntityAlreadyExistsException {
        dao.createNotification(null);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void createNotificationAlreadyExist() throws EntityAlreadyExistsException, SQLException {
        mockMethodIsNotificationExist(true);
        dao.createNotification(notification);
    }

    private void mockMethodIsNotificationExist(boolean isExist) throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(isExist).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getString("notification_type_name")).thenReturn(notification.getType().getName());
        Mockito.when(resultSet.getString("destination_address")).thenReturn(notification.getDestinationAddress());
        Mockito.when(resultSet.getDate("created_at")).thenReturn(new java.sql.Date(notification.getUser().getCreationDate().getTime())).thenReturn(new java.sql.Date(notification.getUser().getCreationDate().getTime()));
        Mockito.when(resultSet.getString("user_name")).thenReturn("usera");
        Mockito.when(resultSet.getString("password")).thenReturn("usera");
        Mockito.when(resultSet.getInt("user_role_id")).thenReturn(1);
        Mockito.when(resultSet.getInt("role_id")).thenReturn(1);
        Mockito.when(resultSet.getString("role_name")).thenReturn("admin");
    }

    @Test(expected = NoEntityExistsException.class)
    public void changeNotificationTypeNotExist() throws SQLException, NoEntityExistsException {
        mockMethodIsNotificationExist(false);
        dao.changeNotificationType(notification, new NotificationType(3, "newType"));
    }
}