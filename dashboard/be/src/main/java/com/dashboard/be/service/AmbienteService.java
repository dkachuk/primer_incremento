package com.dashboard.be.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.dashboard.be.domain.Ambiente;
import com.dashboard.be.repository.AmbienteRepository;

@Service
public class AmbienteService {

    private final AmbienteRepository ambienteRepo;

    public AmbienteService(AmbienteRepository ambienteRepo) {
        this.ambienteRepo = ambienteRepo;
    }

    public List<Ambiente> obtenerTodos() {
        return ambienteRepo.findAll();
    }

    public Optional<Ambiente> obtenerPorId(Long id) {
        return ambienteRepo.findById(id);
    }

    public Ambiente guardar(Ambiente ambiente) {
        return ambienteRepo.save(ambiente);
    }
}
