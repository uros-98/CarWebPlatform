package com.asss.zavrsni.rad.dto;

import lombok.Data;

@Data
public class SendMessageDTO {

    private String content;
    private int conversationId;
    private int senderID;
}
