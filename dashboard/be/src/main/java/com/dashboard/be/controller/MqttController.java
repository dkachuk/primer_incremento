package com.dashboard.be.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.be.domain.mqtt.MqttPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    private final MqttPublisher publisher;

    public MqttController(MqttPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody Map<String, Object> message) {
        try {
            String json_msg = new ObjectMapper().writeValueAsString(message);
            publisher.publish(json_msg);
            return "[BACKEND] Mensaje publicado correctamente.";
        } catch (Exception e) {
            return "[BACKEND] Error: " + e.getMessage();
        }
    }

}
