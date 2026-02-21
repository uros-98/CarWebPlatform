package com.asss.zavrsni.rad.service;

import com.asss.zavrsni.rad.model.Notification;
import com.asss.zavrsni.rad.model.User;
import com.asss.zavrsni.rad.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(User recipient, String title, String message) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setUser(recipient);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationForUser(int userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public void markSingleAsRead(int id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Notification not found with id: " + id));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAsRead(int userId) {
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);

        notifications.stream()
                .filter(n -> !n.isRead())
                .forEach(n -> n.setRead(true));

        notificationRepository.saveAll(notifications);
    }

    public int getUnreadCount(int userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }
}
