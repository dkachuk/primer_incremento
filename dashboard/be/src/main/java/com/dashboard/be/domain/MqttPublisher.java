package com.dashboard.be.domain;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPublisher extends MqttAdapter {

    public MqttPublisher(String topic, String clientId, String username, String password) {
        super(topic, clientId, username, password);
    }

    public void publicarMensaje(String mensaje) throws MqttException {
        // Aquí se implementaría la lógica para publicar el mensaje en el broker MQTT
        
        MqttMessage mqttMessage = new MqttMessage(mensaje.getBytes());
        mqttMessage.setQos(1); // Establecer QoS a 0 (exactamente una vez)
        // mqttMessage.setQos(1); // Establecer QoS a 1 (al menos una vez)

        try {
            MqttClient client = super.getClient();
            client.publish(super.getTopic(), mqttMessage);
            System.out.println("Publicando mensaje: " + mensaje + " en el tópico: " + getTopic());
        } catch (MqttException e) {
            System.err.println("Error al configurar el mensaje MQTT: " + e.getMessage());
        }

    }

}
