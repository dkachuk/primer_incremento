package com.servient.ms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servient.ms.domain.mqtt.MqttPublisher;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    private final MqttPublisher publisher;

    public MqttController(MqttPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        try {
            publisher.publish(message);
            return "[SERVIENT] Mensaje publicado correctamente.";
        } catch (Exception e) {
            return "[SERVIENT] Error: " + e.getMessage();
        }
    }

}
