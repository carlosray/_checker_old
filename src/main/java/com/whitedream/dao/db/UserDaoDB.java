package com.whitedream.dao.db;

import com.whitedream.dao.DaoFactory;
import com.whitedream.dao.RoleDao;
import com.whitedream.dao.UserDao;
import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.Role;
import com.whitedream.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoDB implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoDB.class);
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String GET_USER_BY_NAME = "SELECT * FROM users WHERE user_name = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String UPDATE_USER_PASS = "UPDATE users SET password = ? WHERE user_name = ?";
    private static final String UPDATE_USER_ROLE = "UPDATE users SET user_role_id = ? WHERE user_name = ?";
    private static final String CREATE_USER = "INSERT INTO users (user_name, password, user_role_id, created_at) VALUES (?,?,?,?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE user_name = ?";
    private Connection connection;

    public UserDaoDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User getUser(int id) throws NoEntityExistsException {
        try {
            PreparedStatement ps = connection.prepareStatement(GET_USER_BY_ID);
            return getUser(ps, id);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public User getUser(String userName) throws NoEntityExistsException {
        try {
            PreparedStatement ps = connection.prepareStatement(GET_USER_BY_NAME);
            return getUser(ps, userName);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private User getUser(PreparedStatement ps, Object value) throws SQLException, NoEntityExistsException {
        ps.setObject(1, value);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("user_id");
            String userName = resultSet.getString("user_name");
            String userPass = resultSet.getString("password");
            int roleId = resultSet.getInt("user_role_id");
            Date createdAt = resultSet.getDate("created_at");
            RoleDao roleDaoDB = DaoFactory.getRoleDao(connection);
            Role userRole = roleDaoDB.getRole(roleId);
            User user = new User(id, userName, userPass, userRole, createdAt);
            logger.debug("Успешно запрошен пользователь из БД: " + userName);
            return user;
        } else {
            logger.error("Запрошенный пользователь не найден по значению: " + value);
            throw new NoEntityExistsException("Запрашиваемый пользователь не найден");
        }
    }

    @Override
    public User createUser(User user) throws EntityAlreadyExistsException {
        if (user == null) throw new IllegalArgumentException("Пользователь не может быть null");
        try {
            if (isUserExist(user.getUserName())) throw new EntityAlreadyExistsException("Указанный пользователь уже есть в БД");
            PreparedStatement ps = connection.prepareStatement(CREATE_USER);
            ps.setObject(1, user.getUserName());
            ps.setObject(2, user.getPassword());
            int roleId = user.getRole().getId();
            if (roleId == 0){
                RoleDao roleDao = DaoFactory.getRoleDao(connection);
                Role roleDB = roleDao.getRole(user.getRole().getRoleName());
                roleId = roleDB.getId();
                user.getRole().setId(roleId);
            }
            ps.setInt(3, roleId);
            java.sql.Date creationDate = new java.sql.Date(new Date().getTime());
            ps.setDate(4, creationDate);
            int numberOfUpdated = ps.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId((int) generatedKeys.getLong(1));
                    user.setCreationDate(creationDate);
                } else {
                    throw new SQLException("Не присвоен ID в БД для пользователя");
                }
            }
            logger.debug("Успешно создан пользователь: " + user.getUserName());
            return user;
        } catch (SQLException | NoEntityExistsException e) {
            logger.error("Ошибка при создании пользователя: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void updatePassword(User user, String newPassword) throws NoEntityExistsException {
        if (user == null || newPassword == null) throw new IllegalArgumentException("Пользователь или newPassword не может быть null");
        try {
            if (!isUserExist(user.getUserName())) throw new NoEntityExistsException("Указанного пользователя не существует в БД");
            PreparedStatement ps = connection.prepareStatement(UPDATE_USER_PASS);
            ps.setObject(1, newPassword);
            ps.setObject(2, user.getUserName());
            int numberOfUpdated = ps.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
            logger.debug("Успешно изменен пароль для пользователя: " + user.getUserName());
        } catch (SQLException e) {
            logger.error("Ошибка при изменении пароля пользователя: " + e.getMessage());
        }
    }

    @Override
    public void setRoleToUser(User user, Role role) throws NoEntityExistsException {
        if (user == null || role == null) throw new IllegalArgumentException("Пользователь или роль не может быть null");
        try {
            if (!isUserExist(user.getUserName())) throw new NoEntityExistsException("Указанного пользователя не существует в БД");
            PreparedStatement ps = connection.prepareStatement(UPDATE_USER_ROLE);
            int roleId = role.getId();
            if (roleId == 0){
                RoleDao roleDao = DaoFactory.getRoleDao(connection);
                Role roleDB = roleDao.getRole(role.getRoleName());
                roleId = roleDB.getId();
                user.getRole().setId(roleId);
            }
            ps.setInt(1, roleId);
            ps.setObject(2, user.getUserName());
            int numberOfUpdated = ps.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
            logger.debug("Успешно изменена роль для пользователя: " + user.getUserName());
        } catch (SQLException e) {
            logger.error("Ошибка при изменении роли пользователя: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(User user) throws NoEntityExistsException {
        if (user == null) throw new IllegalArgumentException("Пользователь не может быть null");
        try {
            if (!isUserExist(user.getUserName())) throw new NoEntityExistsException("Указанного пользователя не существует в БД");
            PreparedStatement ps = connection.prepareStatement(DELETE_USER);
            ps.setObject(1, user.getUserName());
            int numberOfUpdated = ps.executeUpdate();
            if (numberOfUpdated == 0) {
                throw new SQLException("Ни одной строки в БД не обновлено");
            }
            logger.debug("Успешно удален пользователь: " + user.getUserName());
        } catch (SQLException e) {
            logger.error("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = ps.executeQuery();
            RoleDao roleDaoDB = DaoFactory.getRoleDao(connection);
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String userPass = resultSet.getString("password");
                int roleId = resultSet.getInt("user_role_id");
                Date createdAt = resultSet.getDate("created_at");
                Role userRole = roleDaoDB.getRole(roleId);
                users.add(new User(id, userName, userPass, userRole, createdAt));
            }
            logger.debug("Успешно запрошен список всех пользователей из БД: ");
        } catch (SQLException | NoEntityExistsException e) {
            logger.error("Ошибка при получении списка пользователей: " + e.getMessage());
        }
        return users;
    }

    //проверка наличия пользователя в базе
    private boolean isUserExist(String userName) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(GET_USER_BY_NAME);
        ps.setObject(1, userName);
        ResultSet resultSet = ps.executeQuery();
        return resultSet.next();
    }
}
