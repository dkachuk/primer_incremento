import org.eclipse.paho.client.mqttv3.*;

public abstract class MqttAdapter {
    protected String topic;
    protected String idClient;
    protected String payload;
    protected String username;
    protected String password;
    protected MqttClient client;

    public MqttAdapter(String topic, String idClient, String username, String password) {
        this.topic = topic;
        this.idClient = idClient;
        this.username = username;
        this.password = password;
    }

    public void establecerConexion(String brokerUrl) throws MqttException {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());

        client = new MqttClient(brokerUrl, idClient);
        client.connect(connOpts);
        System.out.println("Conectado a MQTT Broker: " + brokerUrl);
    }

    public void desconectar() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            System.out.println("Desconectado del broker MQTT.");
        }
    }
}
