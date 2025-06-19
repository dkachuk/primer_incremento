import dashboard.be.src.main.domain.MqttPublisher;

public class Main {
    public static void main(String[] args) throws Exception {
        String broker = "tcp://190.0.100.10:1883";
        String topic = "sga_iot";
        String idClient = "backend-sga";
        String user = "guest";
        String pass = "guest";

        // Publicador
        MqttPublisher publisher = new MqttPublisher(topic, idClient + "-pub", user, pass);
        publisher.establecerConexion(broker);
        publisher.publicarMensaje("{\"device_id\": \"sensor1\", \"value\": 23.4}");
        publisher.desconectar();
    }
}
