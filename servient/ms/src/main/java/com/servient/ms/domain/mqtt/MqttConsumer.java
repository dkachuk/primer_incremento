package com.servient.ms.domain.mqtt;

import java.nio.charset.StandardCharsets;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MqttConsumer extends MqttAdapter {

    protected String topic;

    public MqttConsumer(String brokerUrl, String clientId, String username, String password, String topic) throws Exception {
        this.topic = topic;
        createClient(brokerUrl, clientId, username, password);
    }

    @Override
    public void configure() throws Exception {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.err.println("[SERVIENT] Conexión MQTT perdida: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                // Aquí se enlaza con la lógica de nuestro sistema
                String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
                System.out.println("[SERVIENT] Mensaje recibido en [" + topic + "]: " + payload);

                try {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode json_data = mapper.readTree(payload);

                    System.err.println("[BACKEND] JSON DATA: " + json_data.asText());
                } catch (Exception e) {
                    System.err.println("[BACKEND] Error al procesaro JSON: " + e.getMessage());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
        client.subscribe(this.topic, 1);
        System.out.println("[SERVIENT] Suscripto al topic " + this.topic);
    }
}
