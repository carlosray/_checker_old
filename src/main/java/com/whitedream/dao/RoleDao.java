package com.whitedream.dao;

import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.Role;

public interface RoleDao {
    /**
     * Получить роль по ID
     * @param id id роли
     * @return возвращает роль
     * @throws NoEntityExistsException бросает исключение, если роли не существует
     */
    Role getRole(int id) throws NoEntityExistsException;

    /**
     * Получить роль по имени роли
     * @param roleName имя роли
     * @return возвращает роль
     * @throws NoEntityExistsException бросает исключение, если роли не существует
     */
    Role getRole(String roleName) throws NoEntityExistsException;

    /**
     * изменить имя роли
     * @param role изменяемая роль
     * @param newRoleName новое имя роли
     * @throws NoEntityExistsException бросает исключение, если роли не существует
     */
    void changeRoleName(Role role, String newRoleName) throws NoEntityExistsException;

    /**
     * создать новую роль
     * @param role роль
     * @throws EntityAlreadyExistsException бросает исключение, если такая роль уже есть
     * @return возвращает созданную роль с id
     */
    Role createRole(Role role) throws EntityAlreadyExistsException;

    /**
     * удалить роль
     * @param role роль
     * @throws NoEntityExistsException бросает исключение, если роли не существует
     */
    void deleteRole(Role role) throws NoEntityExistsException;
}
