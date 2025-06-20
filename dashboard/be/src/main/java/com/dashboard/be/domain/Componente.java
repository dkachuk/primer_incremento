package com.dashboard.be.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Componente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int idComponente;
    public String descripcionComponente;
    public String consumoComponente;

    public Componente(int idComponente, String descripcionComponente, String consumoComponente) {
        this.idComponente = idComponente;
        this.descripcionComponente = descripcionComponente;
        this.consumoComponente = consumoComponente;
    }

    public int getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(int idComponente) {
        this.idComponente = idComponente;
    }

    public String getDescripcionComponente() {
        return descripcionComponente;
    }

    public void setDescripcionComponente(String descripcionComponente) {
        this.descripcionComponente = descripcionComponente;
    }

    public String getConsumoComponente() {
        return consumoComponente;
    }

    public void setConsumoComponente(String consumoComponente) {
        this.consumoComponente = consumoComponente;
    }

    public Componente() {
        // Default constructor for JPA
    }

}
