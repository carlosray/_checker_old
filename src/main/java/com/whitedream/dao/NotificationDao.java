package com.whitedream.dao;

import com.whitedream.dao.exception.EntityAlreadyExistsException;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.model.Notification;
import com.whitedream.model.User;

import java.util.List;
import java.util.NoSuchElementException;

public interface NotificationDao {
    /**
     * получение рассылки по ID
     * @param id ID
     * @return вернуть уведомление по ID
     * @throws NoEntityExistsException бросает исключение, если уведомления не существует
     */
    Notification getNotification(int id) throws NoEntityExistsException;

    /**
     * получить все типы уведомлений
     * @return список типов
     */
    List<String> getAllNotificationTypes();

    /**
     * получить уведомление по адресу назначения
     * @param destinationAddress адрес назначения
     * @return возвращает уведомление
     */
    List<Notification> getNotificationsByAddress(String destinationAddress);

    /**
     * получить все уведомления пользователя
     * @param user пользователь
     * @return возвращает уведомление
     */
    List<Notification> getNotificationsByUser(User user);

    /**
     * получить все уведомления по типу
     * @param notificationType тип уведомления
     * @return возвращает уведомление
     */
    List<Notification> getNotificationsByType(String notificationType);

    /**
     * Создать новое уведомление
     * @param notification уведомление
     * @throws EntityAlreadyExistsException бросает исключение, если такое уведомление уже есть в базе
     */
    Notification createNotification(Notification notification) throws EntityAlreadyExistsException;

    /**
     * Изменить тип уведомления
     * @param notification уведомление
     * @param newType новый тип
     * @throws NoEntityExistsException бросает исключение, если уведомления не существует
     */
    void changeNotificationType(Notification notification, String newType) throws NoEntityExistsException;

    /**
     * Изменить адрес назначения
     * @param notification уведомление
     * @param newDestinationAddress новый адрес назначения
     * @throws NoEntityExistsException бросает исключение, если уведомления не существует
     */
    void changeNotificationAddress(Notification notification, String newDestinationAddress) throws NoEntityExistsException;

    /**
     * удалить уведомление
     * @param notification уведомление
     * @throws NoEntityExistsException бросает исключение, если уведомления не существует
     */
    void deleteNotification(Notification notification) throws NoEntityExistsException;

    /**
     * получить список всех существующих уведомлений
     * @return список уведомлений
     */
    List<Notification> getAllNotifications();
}
