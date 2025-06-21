package com.servient.ms.domain.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public abstract class MqttAdapter {

    protected MqttClient client;

    protected MqttClient createClient(String brokerUrl, String clientId, String username, String password) throws Exception {
        client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        client.connect(options);
        return client;
    }

    public MqttClient getClient() {
        return client;
    }

    public abstract void configure() throws Exception;

}
