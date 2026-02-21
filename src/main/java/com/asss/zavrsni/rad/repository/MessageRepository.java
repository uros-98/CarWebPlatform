package com.asss.zavrsni.rad.repository;

import com.asss.zavrsni.rad.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByConversationIdOrderBySentAtAsc(int conversationId);
}
