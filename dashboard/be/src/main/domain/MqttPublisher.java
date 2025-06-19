public class MqttPublisher extends MqttAdapter {

    public MqttPublisher(String topic, String clientId, String username, String password) {
        super(topic, clientId, username, password);
    }

    public void publicarMensaje(String mensaje) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(mensaje.getBytes());
        mqttMessage.setQos(0);
        client.publish(topic, mqttMessage);
        System.out.println("Publicado en topic [" + topic + "]: " + mensaje);
    }
}
