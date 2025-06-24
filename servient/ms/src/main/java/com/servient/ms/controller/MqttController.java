package com.servient.ms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servient.ms.domain.mqtt.MqttPublisher;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    private final MqttPublisher publisher;

    public MqttController(MqttPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        System.out.println(message);
        try {
            String json_msg = new ObjectMapper().writeValueAsString(message);
            System.out.println(json_msg);
            publisher.publish(json_msg);
            return "[SERVIENT] Mensaje publicado correctamente.";
        } catch (Exception e) {
            return "[SERVIENT] Error: " + e.getMessage();
        }
    }

}
