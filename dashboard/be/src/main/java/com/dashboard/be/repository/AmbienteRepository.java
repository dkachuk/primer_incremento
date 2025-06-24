package com.dashboard.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dashboard.be.domain.Ambiente;

public interface AmbienteRepository extends JpaRepository<Ambiente, Long> {
}
