package com.dashboard.be.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dashboard.be.domain.mqtt.MqttConsumer;
import com.dashboard.be.domain.mqtt.MqttPublisher;
import com.dashboard.be.event.WebSocketEventPublisher;
import com.dashboard.be.repository.CosaRepository;

@Configuration
public class MqttAdapterConfig {

    @Value("${mqtt.broker-url}")
    protected String brokerUrl;
    @Value("${mqtt.username}")
    protected String username;
    @Value("${mqtt.password}")
    protected String password;
    @Value("${mqtt.clientid-DtoS}")
    protected String clientId_DtoS;
    @Value("${mqtt.topic-DtoS}")
    protected String topic_DtoS;
    @Value("${mqtt.clientid-StoD}")
    protected String clientId_StoD;
    @Value("${mqtt.topic-StoD}")
    protected String topic_StoD;

    @Bean
    public MqttConsumer mqttConsumer(CosaRepository cosaRepository, WebSocketEventPublisher wse_publisher) {
        System.out.println("[BACKEND] Creando bean MqttConsumer");
        try {
            MqttConsumer consumer = new MqttConsumer(this.brokerUrl, this.clientId_StoD, this.username, this.password, this.topic_StoD, cosaRepository, wse_publisher);
            consumer.configure();
            return consumer;
        } catch (Exception e) {
            System.out.println("[BACKEND] Error al crear el bean MqttConsumer");
            return null;
        }
    }

    @Bean
    public MqttPublisher mqttPublisher() {
        System.out.println("[BACKEND] Creando bean MqttPublisher");
        try {
            MqttPublisher publisher = new MqttPublisher(this.brokerUrl, this.clientId_DtoS, this.username, this.password, this.topic_DtoS);
            publisher.configure(); // opcional
            return publisher;
        } catch (Exception e) {
            System.out.println("[BACKEND] Error al crear el bean MqttPublisher");
            return null;
        }
    }
}
