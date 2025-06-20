package com.dashboard.be;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.be.domain.MqttPublisher;
import com.dashboard.be.domain.Test;

@RestController
public class HealthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/")
    public String health() {
        return "OK";
    }

     @PostMapping("/db")
    public String db() {
        String sql = "SELECT * FROM SGAIOT.TEST";
        List<Test> lista = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Test.class));
        return lista.toString();
    }
    
    @RequestMapping("/broker")
    public String broker() throws Exception {
        // Aquí se implementaría la lógica para verificar el estado del broker MQTT
        // Por ahora, retornamos un mensaje de ejemplo como placeholder
        MqttPublisher mqttPublisher = new MqttPublisher("sga_iot", "backend-sga-pub", "guest", "guest");
        try {
            mqttPublisher.establecerConexion("tcp://localhost:1883");
            mqttPublisher.publicarMensaje("{\"temp\":25.6}");
        } catch (Exception e) {
            System.err.println("Error al publicar mensaje: " + e.getMessage());
        }

        return "Broker MQTT está activo";
    }
}
