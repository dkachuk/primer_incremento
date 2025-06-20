package com.dashboard.be.domain;

public class MqttConsumer extends MqttAdapter {

    public MqttConsumer(String topic, String clientId, String username, String password) {
        super(topic, clientId, username, password);
    }

    public String obtenerMensaje() {
        // Aquí se implementaría la lógica para obtener el mensaje del broker MQTT
        // Por ahora, retornamos un mensaje de ejemplo como placeholder
        return "Mensaje recibido desde el broker MQTT";
    }
}
