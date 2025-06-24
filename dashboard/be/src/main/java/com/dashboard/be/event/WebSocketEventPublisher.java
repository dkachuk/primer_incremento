package com.dashboard.be.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketEventPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void enviarEvento(Object evento) {
        messagingTemplate.convertAndSend("/topic/actualizaciones", evento);
    }
}
