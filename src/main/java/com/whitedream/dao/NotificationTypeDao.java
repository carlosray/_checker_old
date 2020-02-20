package com.whitedream.dao;

import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.NotificationType;

import java.util.List;

public interface NotificationTypeDao {
    List<NotificationType> getAllNotificationTypes();

    /**
     * Получить тип уведомления по ID
     * @param id id типа уведомления
     * @return возвращает тип уведомления
     * @throws NoEntityExistsException бросает исключение, если типа уведомления не существует
     */
    NotificationType getNotificationType(int id) throws NoEntityExistsException;

    /**
     * Получить тип уведомления по имени
     * @param name name типа уведомления
     * @return возвращает тип уведомления
     * @throws NoEntityExistsException бросает исключение, если типа уведомления не существует
     */
    NotificationType getNotificationType(String name) throws NoEntityExistsException;

    /**
     * Создать новый тип уведомления
     * @param notificationType создаваемый тип уведомления
     * @return возвращает созданный тип уведомления
     * @throws EntityAlreadyExistsException бросает исключение, если такой тип уведомления уже есть в базе
     */
    NotificationType createNotificationType(NotificationType notificationType) throws EntityAlreadyExistsException;

    /**
     * Изменить имя типа уведомления
     * @param notificationType изменяемое уведомление
     * @param newName новое имя
     * @throws NoEntityExistsException бросает исключение, если типа уведомления не существует
     */
    void changeNotificationTypeName(NotificationType notificationType, String newName) throws NoEntityExistsException;

    /**
     * удалить тип уведомления
     * @param notificationType удаляемый тип уведомления
     * @throws NoEntityExistsException бросает исключение, если типа уведомления не существует
     */
    void deleteNotificationType(NotificationType notificationType) throws NoEntityExistsException;
}
