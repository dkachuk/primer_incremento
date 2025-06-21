package com.servient.ms.domain.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPublisher extends MqttAdapter {

    protected String topic;

    public MqttPublisher(String brokerUrl, String clientId, String username, String password, String topic) throws Exception {
        this.topic = topic;
        createClient(brokerUrl, clientId, username, password);
    }

    @Override
    public void configure() throws Exception {
        // Aquí se pueden agregar configuraciones adicionales si es necesario
    }

    public void publish(String payload) throws Exception {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);
        client.publish(this.topic, message);
        System.out.println("[BACKEND] Mensaje publicado en el topic " + this.topic);
    }

}
