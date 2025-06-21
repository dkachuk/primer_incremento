package com.dashboard.be.domain.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

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
                System.err.println("[BACKEND] Conexión MQTT perdida: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                // Aquí se enlaza con la lógica de nuestro sistema
                System.out.println("[BACKEND] Mensaje recibido en [" + topic + "]: " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
        client.subscribe(this.topic, 1);
        System.out.println("[BACKEND] Suscripto al topic " + this.topic);
    }
}
