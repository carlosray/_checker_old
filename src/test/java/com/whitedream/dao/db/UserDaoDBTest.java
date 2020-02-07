package com.whitedream.dao.db;

import com.whitedream.dao.UserDao;
import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
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
public class UserDaoDBTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private UserDao dao;
    private User usera;
    private User userb;

    @Before
    public void setUp() throws Exception {
        Mockito.when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);

        dao = new UserDaoDB(connection);
        usera = new User(1, "usera", "usera", new Role("admin"), new Date());
        userb = new User(2, "userb", "userb", new Role("member"), new Date());
    }

    @Test(expected=NoEntityExistsException.class)
    public void getUserByWrongId() throws NoEntityExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(false);
        dao.getUser(1);
    }


    @Test
    public void getUserById() throws NoEntityExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true);
        Mockito.when(resultSet.getString("user_name")).thenReturn(usera.getUserName());
        Mockito.when(resultSet.getString("password")).thenReturn(usera.getPassword());
        int role_id = 1;
        Mockito.when(resultSet.getInt("user_role_id")).thenReturn(role_id);
        Mockito.when(resultSet.getInt("role_id")).thenReturn(role_id);
        Mockito.when(resultSet.getString("role_name")).thenReturn(usera.getRole().getRoleName());

        User useraTest = dao.getUser(1);
        Assert.assertEquals(usera, useraTest);
        Assert.assertNotEquals(userb, useraTest);
    }

    @Test(expected=IllegalArgumentException.class)
    public void createUserNullThrowsException() throws EntityAlreadyExistsException {
        dao.createUser(null);
    }

    @Test(expected=EntityAlreadyExistsException.class)
    public void createUserExistsThrowsException() throws EntityAlreadyExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        dao.createUser(usera);
    }

    @Test
    public void createUser() throws EntityAlreadyExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(false).thenReturn(true);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        User useraTest = dao.createUser(usera);
        Assert.assertNotNull(useraTest);
        Assert.assertEquals(usera, useraTest);
    }

    @Test
    public void createUserNoUpdatedRowsInDB() throws EntityAlreadyExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(false).thenReturn(false);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        User useraTest = dao.createUser(usera);
        Assert.assertNull(useraTest);
    }

    @Test(expected=IllegalArgumentException.class)
    public void updatePasswordNullUserThrowsException() throws NoEntityExistsException {
        dao.updatePassword(null, "asd");
    }

    @Test(expected=IllegalArgumentException.class)
    public void updatePasswordNullPwdThrowsException() throws NoEntityExistsException {
        dao.updatePassword(usera, null);
    }

    @Test(expected=NoEntityExistsException.class)
    public void updatePasswordUserNotExistsThrowsException() throws NoEntityExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(false);
        dao.updatePassword(usera, "123");
    }

    @Test(expected=IllegalArgumentException.class)
    public void setRoleToUserNullThrowsException() throws NoEntityExistsException {
        dao.setRoleToUser(null, null);
    }

    @Test(expected=NoEntityExistsException.class)
    public void setRoleToUserNotExistThrowsException() throws NoEntityExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(false);
        dao.setRoleToUser(usera, userb.getRole());
    }

    @Test
    public void setRoleToUser() throws NoEntityExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true);
        Mockito.when(resultSet.getInt("role_id")).thenReturn(1);
        Mockito.when(resultSet.getString("role_name")).thenReturn("admin");
        dao.setRoleToUser(usera, userb.getRole());
    }

    @Test(expected=IllegalArgumentException.class)
    public void deleteUserNullThrowsException() throws NoEntityExistsException {
        dao.deleteUser(null);
    }

    @Test(expected=NoEntityExistsException.class)
    public void deleteUserNotExistsThrowsException() throws NoEntityExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(false);
        dao.deleteUser(usera);
    }

    @Test
    public void deleteUser() throws NoEntityExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        dao.deleteUser(usera);
    }
}