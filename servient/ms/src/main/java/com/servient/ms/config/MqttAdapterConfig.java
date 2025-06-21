package com.servient.ms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.servient.ms.domain.mqtt.MqttConsumer;
import com.servient.ms.domain.mqtt.MqttPublisher;

@Configuration
public class MqttAdapterConfig {

    @Value("${mqtt.broker-url}")
    protected String brokerUrl;
    @Value("${mqtt.clientid-DtoS}")
    protected String clientId_DtoS;
    @Value("${mqtt.clientid-StoD}")
    protected String clientId_StoD;
    @Value("${mqtt.topic-DtoS}")
    protected String topic_DtoS;
    @Value("${mqtt.topic-StoD}")
    protected String topic_StoD;
    @Value("${mqtt.username}")
    protected String username;
    @Value("${mqtt.password}")
    protected String password;

    @Bean
    public MqttConsumer mqttConsumer() {
        System.out.println("[SERVIENT] Creando bean MqttConsumer");
        try {
            MqttConsumer consumer = new MqttConsumer(this.brokerUrl, this.clientId_DtoS, this.username, this.password, this.topic_DtoS);
            consumer.configure();
            return consumer;
        } catch (Exception e) {
            System.out.println("[SERVIENT] Error al crear el bean MqttConsumer");
            return null;
        }
    }

    @Bean
    public MqttPublisher mqttPublisher() {
        System.out.println("[SERVIENT] Creando bean MqttPublisher");
        try {
            MqttPublisher publisher = new MqttPublisher(this.brokerUrl, this.clientId_StoD, this.username, this.password, this.topic_StoD);
            publisher.configure(); // opcional
            return publisher;
        } catch (Exception e) {
            System.out.println("[SERVIENT] Error al crear el bean MqttPublisher");
            return null;
        }
    }
}
