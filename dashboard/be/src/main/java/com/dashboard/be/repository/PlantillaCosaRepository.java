package com.dashboard.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.dashboard.be.domain.PlantillaCosa;

public interface PlantillaCosaRepository extends JpaRepository<PlantillaCosa, Long> {

    Optional<PlantillaCosa> findByNombre(String nombre);
}
