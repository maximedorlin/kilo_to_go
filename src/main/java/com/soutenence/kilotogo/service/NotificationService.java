package com.soutenence.kilotogo.service;

import com.soutenence.kilotogo.entity.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationService {
    List<Notification> getAllNotifications();
    Optional<Notification> getNotificationById(Long id);
    Notification createNotification(Notification notification);
    Notification updateNotification(Long id, Notification notificationDetails);
    void deleteNotification(Long id);
    Notification partialUpdateNotification(Long id, Notification notificationUpdates);
}
