package com.dashboard.be.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.dashboard.be.domain.Cosa;
import com.dashboard.be.domain.mqtt.MqttPublisher;
import com.dashboard.be.dto.CambioEstadoPayload;
import com.dashboard.be.repository.CosaRepository;

@Service
public class CosaService {

    private final CosaRepository cosaRepo;

    @Autowired
    private MqttPublisher mqttPublisher;

    @Autowired
    private ObjectMapper objectMapper;

    public CosaService(CosaRepository cosaRepo) {
        this.cosaRepo = cosaRepo;
    }

    public Cosa guardar(Cosa cosa) {
        return cosaRepo.save(cosa);
    }

    public Optional<Cosa> obtenerPorId(Long id) {
        return cosaRepo.findById(id);
    }

    public void cambiarEstado(Long id, boolean nuevoEstado) {
        Cosa cosa = cosaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cosa no encontrada con id: " + id));

        cosa.setState(nuevoEstado);
        cosaRepo.save(cosa);
        CambioEstadoPayload payload = new CambioEstadoPayload(id, nuevoEstado);
        try {
            String json = objectMapper.writeValueAsString(payload);
            mqttPublisher.publish(json);
            System.out.println("[BACKEND] Estado de cosa publicado por MQTT: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
