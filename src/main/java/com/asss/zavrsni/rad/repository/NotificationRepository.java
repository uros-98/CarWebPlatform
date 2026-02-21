package com.asss.zavrsni.rad.repository;

import com.asss.zavrsni.rad.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(int userId);

    int countByUserIdAndIsReadFalse(int userId);
}
