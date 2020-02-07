package com.whitedream.dao.db;

import com.whitedream.dao.RoleDao;
import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.Role;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDaoDB implements RoleDao {
    private static final Logger logger = Logger.getLogger(RoleDaoDB.class);
    private static final String GET_ROLE_BY_ID = "SELECT * FROM roles WHERE role_id = ?";
    private static final String GET_ROLE_BY_NAME = "SELECT * FROM roles WHERE role_name = ?";
    private static final String CHANGE_ROLE_NAME = "UPDATE roles SET role_name = ? WHERE role_name = ?";
    private static final String CREATE_ROLE = "INSERT INTO roles (role_name) VALUES (?)";
    private static final String DELETE_ROLE = "DELETE FROM roles WHERE role_name = ?";
    private Connection connection;

    public RoleDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Role getRole(int id) throws NoEntityExistsException {
        try {
            PreparedStatement ps = connection.prepareStatement(GET_ROLE_BY_ID);
            return getRole(ps, id);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Role getRole(String roleName) throws NoEntityExistsException {
        try {
            PreparedStatement ps = connection.prepareStatement(GET_ROLE_BY_NAME);
            return getRole(ps, roleName);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private Role getRole(PreparedStatement ps, Object value) throws SQLException, NoEntityExistsException {
        ps.setObject(1, value);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("role_id");
            String roleName = resultSet.getString("role_name");
            Role role = new Role(id, roleName);
            logger.debug("Успешно запрошена роль из БД: " + roleName);
            return role;
        } else {
            logger.error("Запрошенная роль не найдена по значению: " + value);
            throw new NoEntityExistsException("Запрашиваемая роль не найдена");
        }
    }

    @Override
    public void changeRoleName(Role role, String newRoleName) throws NoEntityExistsException {
        if (role == null || newRoleName == null) throw new IllegalArgumentException("Роль или новое имя роли не может быть null");
        try {
            if (!isRoleExist(role.getRoleName())) throw new NoEntityExistsException("Указанной роли не существует в БД");
            PreparedStatement ps = connection.prepareStatement(CHANGE_ROLE_NAME);
            ps.setObject(1, newRoleName);
            ps.setObject(2, role.getRoleName());
            int updated = ps.executeUpdate();
            logger.debug(String.format("Успешно изменена роль '%s'. Изменено название на '%s'. Обновлено строк %d", role.getRoleName(), newRoleName, updated));
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Role createRole(Role role) throws EntityAlreadyExistsException {
        if (role == null) throw new IllegalArgumentException("Роль не может быть null");
        try {
            if (isRoleExist(role.getRoleName())) throw new EntityAlreadyExistsException("Указанная роль уже существует в БД");
            PreparedStatement ps = connection.prepareStatement(CREATE_ROLE);
            ps.setObject(1, role.getRoleName());
            int numberOfUpdated = ps.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    role.setId((int) generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Не присвоен ID в БД для уведомления");
                }
            }
            logger.debug(String.format("Успешно создана роль '%s'. Обновлено строк %d", role.getRoleName(), numberOfUpdated));
            return role;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteRole(Role role) throws NoEntityExistsException {
        if (role == null) throw new IllegalArgumentException("Роль не может быть null");
        try {
            if (!isRoleExist(role.getRoleName())) throw new NoEntityExistsException("Указанной роли не существует в БД");
            PreparedStatement ps = connection.prepareStatement(DELETE_ROLE);
            ps.setObject(1, role.getRoleName());
            int updated = ps.executeUpdate();
            logger.debug(String.format("Успешно удалена роль '%s'. Обновлено строк %d", role.getRoleName(), updated));
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    //проверка наличия роли в базе
    private boolean isRoleExist(String roleName) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(GET_ROLE_BY_NAME);
        ps.setObject(1, roleName);
        ResultSet resultSet = ps.executeQuery();
        return resultSet.next();
    }
}
