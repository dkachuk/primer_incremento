package com.dashboard.be.domain.mqtt;

import java.nio.charset.StandardCharsets;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.dashboard.be.domain.Cosa;
import com.dashboard.be.event.WebSocketEventPublisher;
import com.dashboard.be.repository.CosaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MqttConsumer extends MqttAdapter {

    protected String topic;
    private final CosaRepository cosaRepository;
    private final WebSocketEventPublisher wse_publisher;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MqttConsumer(String brokerUrl,
            String clientId,
            String username,
            String password,
            String topic,
            CosaRepository cosaRepository,
            WebSocketEventPublisher wse_publisher) throws Exception {
        this.topic = topic;
        this.cosaRepository = cosaRepository;
        this.wse_publisher = wse_publisher;
        createClient(brokerUrl, clientId, username, password);
        System.out.println("[DEBUG] Instancia de MqttConsumer creada con topic: " + topic);
    }

    @Override
    public void configure() throws Exception {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.err.println("[BACKEND] Conexión MQTT perdida: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                // Aquí se enlaza con la lógica de nuestro sistema
                String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
                System.out.println("[BACKEND] Mensaje recibido en [" + topic + "]: " + payload);

                try {
                    JsonNode json_data = objectMapper.readTree(payload);

                    if (json_data.has("evento") && "estado-reportado".equals(json_data.get("evento").asText())) {
                        Long cosaId = json_data.get("cosaId").asLong();
                        boolean estado = json_data.get("estadoActual").asBoolean();

                        Cosa cosa = cosaRepository.findById(cosaId).orElseThrow(() -> new RuntimeException("Cosa no encontrada con ID: " + cosaId));
                        cosa.setState(estado);
                        cosaRepository.save(cosa);

                        System.out.println("[BACKEND] Estado actualizado desde MQTT para cosa ID: " + cosaId);

                        try {
                            wse_publisher.enviarEvento(payload);
                        } catch (Exception ex) {
                            System.err.println("[BACKEND] Error al enviar evento WebSocket:");
                            ex.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    System.err.println("[BACKEND] Error al procesar mensaje MQTT");
                    e.printStackTrace();
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
        System.out.println("[DEBUG] Suscribiéndose al topic: " + this.topic);
        client.subscribe(this.topic, 1);
        System.out.println("[BACKEND] Suscripto al topic " + this.topic);
        System.out.println("[DEBUG] Registrando callback y suscribiéndose...");
        System.out.println("[DEBUG] ¿Cliente sigue conectado?: " + client.isConnected());
    }
}
