package com.dashboard.be.domain;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttAdapter {

    private MqttClient client;
    private String topic;
    private String clientId;
    private String payload;
    private String username;
    private String password;

    public MqttAdapter(String topic, String clientId, String username, String password) {
        // Constructor por defecto
        this.topic = topic;
        this.clientId = clientId;
        this.payload = null; // Inicializar payload como null
        this.username = username;
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MqttClient getClient() {
        return this.client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

    public boolean establecerConexion(String urlConexion, String fromClass) throws MqttException {
        // Aquí se implementaría la lógica para establecer la conexión MQTT
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(this.username);
        options.setPassword(this.password.toCharArray());

        this.client = new MqttClient(urlConexion, this.clientId);
        try {
            this.client.connect(options);
            System.out.println(fromClass + ": Conexión establecida con el broker MQTT.");
            return true;
        } catch (MqttException e) {
            System.err.println(fromClass + ": Error al conectar al broker MQTT: " + e.getMessage());
            return false;
        }
    }

    public boolean desconectar(String fromClass) throws MqttException {
        // Aquí se implementaría la lógica para desconectar del broker MQTT
        if (this.client != null && this.client.isConnected()) {
            try {
                this.client.disconnect();
                System.out.println(fromClass + ": Desconexión exitosa del broker MQTT.");
                return true;
            } catch (MqttException e) {
                System.err.println(fromClass + ": Error al desconectar del broker MQTT: " + e.getMessage());
            }
        }
        return false;
    }

}
