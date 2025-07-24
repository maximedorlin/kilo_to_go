package com.soutenence.kilotogo.serviceImpl;

import com.soutenence.kilotogo.entity.Notification;
import com.soutenence.kilotogo.repository.NotificationRepository;
import com.soutenence.kilotogo.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateNotification(Long id, Notification notificationDetails) {
        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        BeanUtils.copyProperties(notificationDetails, existingNotification, "id", "utilisateur", "dateCreation");
        return notificationRepository.save(existingNotification);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public Notification partialUpdateNotification(Long id, Notification notificationUpdates) {
        return notificationRepository.findById(id).map(existingNotification -> {
            if (notificationUpdates.getType() != null) existingNotification.setType(notificationUpdates.getType());
            if (notificationUpdates.getContenu() != null) existingNotification.setContenu(notificationUpdates.getContenu());
            if (notificationUpdates.getLien() != null) existingNotification.setLien(notificationUpdates.getLien());
            if (notificationUpdates.getLue() != null) existingNotification.setLue(notificationUpdates.getLue());
            return notificationRepository.save(existingNotification);
        }).orElseThrow(() -> new RuntimeException("Notification not found"));
    }
}
