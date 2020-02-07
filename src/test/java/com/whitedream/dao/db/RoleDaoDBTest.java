package com.whitedream.dao.db;

import com.whitedream.dao.RoleDao;
import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.Role;
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

import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class RoleDaoDBTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private RoleDao dao;
    private Role roleAdmin;
    private Role roleMember;

    @Before
    public void setUp() throws Exception {
        Mockito.when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        dao = new RoleDaoDB(connection);
        roleAdmin = new Role(1, "admin");
        roleMember = new Role(2, "member");
    }

    @Test(expected=NoEntityExistsException.class)
    public void getRoleByIdWrongId() throws SQLException, NoEntityExistsException {
        Mockito.when(resultSet.next()).thenReturn(false);
        RoleDao dao = new RoleDaoDB(connection);
        dao.getRole("3");
    }

    @Test
    public void getRoleById() throws SQLException, NoEntityExistsException {
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("role_id")).thenReturn(1);
        Mockito.when(resultSet.getString("role_name")).thenReturn("admin");
        Role roleAdminTest = dao.getRole(1);
        Assert.assertEquals(roleAdmin, roleAdminTest);
        Mockito.when(resultSet.getInt("role_id")).thenReturn(2);
        Mockito.when(resultSet.getString("role_name")).thenReturn("member");
        Role roleMemberTest = dao.getRole(2);
        Assert.assertEquals(roleMember, roleMemberTest);
    }

    @Test
    public void getRoleByName() throws SQLException, NoEntityExistsException {
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("role_id")).thenReturn(1);
        Mockito.when(resultSet.getString("role_name")).thenReturn("admin");
        Role roleAdminTest = dao.getRole("admin");
        Assert.assertEquals(roleAdmin, roleAdminTest);
        Mockito.when(resultSet.getInt("role_id")).thenReturn(2);
        Mockito.when(resultSet.getString("role_name")).thenReturn("member");
        Role roleMemberTest = dao.getRole("member");
        Assert.assertEquals(roleMember, roleMemberTest);
    }

    @Test(expected=IllegalArgumentException.class)
    public void createRoleNullThrowsException() throws EntityAlreadyExistsException {
        dao.createRole(null);
    }

    @Test(expected=EntityAlreadyExistsException.class)
    public void createExistsRoleThrowsException() throws SQLException, EntityAlreadyExistsException {
        Mockito.when(resultSet.next()).thenReturn(true);
        dao.createRole(roleAdmin);
    }

    @Test
    public void createRole() throws SQLException, EntityAlreadyExistsException {
        Mockito.when(resultSet.next()).thenReturn(false).thenReturn(true);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        Role roleAdminTest = dao.createRole(roleAdmin);
        Assert.assertEquals(roleAdmin, roleAdminTest);
    }

    @Test(expected=IllegalArgumentException.class)
    public void changeRoleNameNullThrowsException() throws NoEntityExistsException {
        dao.changeRoleName(null, "sd");
    }

    @Test(expected=NoEntityExistsException.class)
    public void changeRoleNameNotExistThrowsException() throws NoEntityExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(false);
        dao.changeRoleName(roleAdmin, "newName");
    }

    @Test
    public void changeRoleName() throws NoEntityExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        dao.changeRoleName(roleAdmin, "newName");
    }

    @Test(expected=NoEntityExistsException.class)
    public void deleteRoleNotExistThrowsException() throws NoEntityExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(false);
        dao.deleteRole(roleAdmin);
    }

    @Test(expected=IllegalArgumentException.class)
    public void deleteRoleNullThrowsException() throws NoEntityExistsException {
        dao.deleteRole(null);
    }

    @Test
    public void deleteRole() throws NoEntityExistsException, SQLException {
        Mockito.when(resultSet.next()).thenReturn(true);
        dao.deleteRole(roleAdmin);
    }
}