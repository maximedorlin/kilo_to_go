package com.soutenence.kilotogo.repository;

import com.soutenence.kilotogo.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
