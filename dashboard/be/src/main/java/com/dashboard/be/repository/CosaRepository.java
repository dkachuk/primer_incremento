package com.dashboard.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dashboard.be.domain.Cosa;

public interface CosaRepository extends JpaRepository<Cosa, Long> {
}