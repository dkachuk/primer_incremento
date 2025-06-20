package com.dashboard.be;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.dashboard.be.domain.MqttConsumer;

@Component
public class MqttConsumerRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        new Thread(() -> {
            try {
                MqttConsumer consumer = new MqttConsumer(
                    "sga_iot",
                    "backend-sga-sub",
                    "guest",
                    "guest"
                );

                consumer.establecerConexion("tcp://190.0.100.10:1883", "Consumer Runner");
                consumer.obtenerMensaje();

                // Mantener hilo activo
                while (true) {
                    Thread.sleep(1000);
                }

            } catch (Exception e) {
                System.err.println("Error en MqttConsumer: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

}
