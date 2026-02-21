package com.asss.zavrsni.rad.controller;

import com.asss.zavrsni.rad.dto.SendMessageDTO;
import com.asss.zavrsni.rad.model.Conversation;
import com.asss.zavrsni.rad.model.Message;
import com.asss.zavrsni.rad.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conversation")
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping("/start")
    public ResponseEntity<?> startConversation(@RequestParam int vehicleId, @RequestParam int buyerId, @RequestParam String firstMessage) {
        try {
            Conversation newConversation = conversationService.startNewConversation(vehicleId, buyerId, firstMessage);
            return new ResponseEntity<>(newConversation, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error starting conversation " + e.getMessage());
        }
    }

    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageDTO sendMessageDTO) {
        try {
            Message newMessage = conversationService.sendMessage(sendMessageDTO);
            return new ResponseEntity<>(newMessage, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending message: " + e.getMessage());
        }
    }

    @GetMapping("/{conversationId}/message")
    public ResponseEntity<List<Message>> getConversationMessage(@PathVariable int conversationId) {
        List<Message> getMessages = conversationService.getMessageAndMarkRead(conversationId);
        if (getMessages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(getMessages);
    }
}