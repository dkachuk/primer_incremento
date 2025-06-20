package com.dashboard.be.domain;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttConsumer extends MqttAdapter {

    public MqttConsumer(String topic, String clientId, String username, String password) {
        super(topic, clientId, username, password);
    }

    public String obtenerMensaje() throws MqttException {
        // Aquí se implementaría la lógica para obtener el mensaje del broker MQTT

        try {
            MqttClient client = super.getClient();
            client.subscribe(super.getTopic(), (topic, message) -> {
                // Aquí se implementaría la lógica para manejar el mensaje recibido
                String msg = new String(message.getPayload());
                System.out.println("Mensaje recibido en el tópico " + topic + ": " + msg);
            });
        } catch (MqttException e) {
            System.err.println("Error al suscribirse al tópico: " + e.getMessage());
        }
        return "Mensaje recibido desde el broker MQTT";
    }
}
