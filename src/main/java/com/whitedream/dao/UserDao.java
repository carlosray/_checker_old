package com.whitedream.dao;

import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.Role;
import com.whitedream.model.User;

import java.util.List;

public interface UserDao {
    /**
     * Получение юзера по ID
     * @param id ID пользователя
     * @return User
     * @throws NoEntityExistsException бросает исключение, если пользователя не существует
     */
    User getUser(int id) throws NoEntityExistsException;

    /**
     * Получение юзера user Name
     * @param userName имя пользователя
     * @return User
     * @throws NoEntityExistsException бросает исключение, если пользователя не существует
     */
    User getUser(String userName) throws NoEntityExistsException;

    /**
     * Создание пользователя
     * @param user Пользователь
     * @return возвращает созданного пользователя (+ ID + creationDate)
     * @throws EntityAlreadyExistsException бросает исключение, если такой пользователь уже есть в базе
     */
    User createUser(User user) throws EntityAlreadyExistsException;

    /**
     * Метод изменения пароля
     * @param user Пользователь
     * @param newPassword новый пароль
     * @throws NoEntityExistsException бросает исключение, если пользователя не существует
     */
    void updatePassword(User user, String newPassword) throws NoEntityExistsException;

    /**
     * Предоставление роли для пользователя
     * @param user Пользователь
     * @param role предоставляемая роль
     * @throws NoEntityExistsException бросает исключение, если пользователя не существует
     */
    void setRoleToUser(User user, Role role) throws NoEntityExistsException;

    /**
     * Удаление пользователя
     * @param user пользователь
     * @throws NoEntityExistsException бросает исключение, если пользователя не существует
     */
    void deleteUser(User user) throws NoEntityExistsException;

    /**
     * Все пользователи
     * @return возвращает список всех пользователей
     */
    List<User> getAllUsers();
}
