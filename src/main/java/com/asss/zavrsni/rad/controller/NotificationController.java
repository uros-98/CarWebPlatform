package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.model.Notification;
import com.asss.zavrsni.rad.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationForUser(@PathVariable int userId) {
        List<Notification> notifications = notificationService.getNotificationForUser(userId);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/user/{userId}/mark-read")
    public ResponseEntity<Void> markAllAsRead(@PathVariable int userId) {
        notificationService.markAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}/unread-content")
    public ResponseEntity<Integer> getUnreadCount(@PathVariable int userId) {
        int count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markSingleAsRead(@PathVariable int id) {
        notificationService.markSingleAsRead(id);
        return ResponseEntity.ok().build();
    }
}