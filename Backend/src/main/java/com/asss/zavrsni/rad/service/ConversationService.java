package com.asss.zavrsni.rad.service;

import com.asss.zavrsni.rad.dto.SendMessageDTO;
import com.asss.zavrsni.rad.model.Conversation;
import com.asss.zavrsni.rad.model.Message;
import com.asss.zavrsni.rad.model.User;
import com.asss.zavrsni.rad.model.Vehicle;
import com.asss.zavrsni.rad.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    public Conversation startNewConversation(int vehicleId, int buyerId, String firstMessage) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NoSuchElementException("Vehicle not found"));

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Conversation conversation = new Conversation();
        conversation.setVehicle(vehicle);
        conversation.setSubject("Vehicle inquiry: " + vehicle.getMake() + " " + vehicle.getModel());
        conversation.setCreatedAt(LocalDateTime.now());
        Conversation savedConversation = conversationRepository.save(conversation);

        Message message = new Message();
        message.setContent(firstMessage);
        message.setSentAt(LocalDateTime.now());
        message.setConversation(savedConversation);
        message.setSender(buyer);
        message.setRead(false);
        messageRepository.save(message);

        notificationService.sendNotification(vehicle.getSeller(), "New message ",
                "You have new message from user: " + buyer.getUsername() + " regarding the vehicle");

        return savedConversation;
    }

    @Transactional
    public Message sendMessage(SendMessageDTO sendMessageDTO) {
        Conversation conversation = conversationRepository.findById(sendMessageDTO.getConversationId())
                .orElseThrow(() -> new NoSuchElementException("Conversation not found"));

        User sender = userRepository.findById(sendMessageDTO.getSenderID())
                .orElseThrow(() -> new NoSuchElementException("Sender not found"));

        Message message = new Message();
        message.setContent(sendMessageDTO.getContent());
        message.setSentAt(LocalDateTime.now());
        message.setContent(String.valueOf(conversation));
        message.setSender(sender);
        message.setRead(false);

        Message savedMessage = messageRepository.save(message);

        User vehicleSeller = conversation.getVehicle().getSeller();
        User otherUser = (sender.getId() == vehicleSeller.getId())
                ? conversation.getMessages().stream()
                .map(Message::getSender)
                .filter(u -> u.getId() != sender.getId())
                .findFirst().orElse(null)
                : vehicleSeller;

        if (otherUser != null) {
            notificationService.sendNotification(otherUser, "New message", "You recived new message in chat: " + conversation.getSubject());
        }

        return savedMessage;
    }

    @Transactional
    public List<Message> getMessageAndMarkRead(int conversationId) {
        List<Message> messages = messageRepository.findByConversationIdOrderBySentAtAsc(conversationId);

        messages.stream()
                .filter(message -> !message.isRead())
                .forEach(message -> message.setRead(true));

        return messages;
    }
}
