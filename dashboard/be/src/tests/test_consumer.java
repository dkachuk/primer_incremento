import dashboard.be.src.main.domain.MqttConsumer;

public class Main {
    public static void main(String[] args) throws Exception {
        String broker = "tcp://190.0.100.10:1883";
        String topic = "sga_iot";
        String clientId = "backend-sga";
        String user = "guest";
        String pass = "guest";

        // Consumidor
        MqttConsumer consumer = new MqttConsumer(topic, clientId + "-sub", user, pass);
        consumer.establecerConexion(broker);
        consumer.obtenerMensajes();

        // Dejar escuchando...
        Thread.sleep(10000);
        consumer.desconectar();
    }
}
