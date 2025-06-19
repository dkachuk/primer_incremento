public class MqttConsumer extends MqttAdapter {

    public MqttConsumer(String topic, String clientId, String username, String password) {
        super(topic, clientId, username, password);
    }

    public void obtenerMensajes() throws MqttException {
        client.subscribe(topic, (t, msg) -> {
            System.out.println("Mensaje recibido en topic [" + t + "]: " + new String(msg.getPayload()));
            // Acá podrías llamar a una función que inserte en Oracle, por ejemplo
        });
    }
}
