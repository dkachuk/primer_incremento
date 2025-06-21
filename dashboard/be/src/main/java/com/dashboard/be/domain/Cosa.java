package com.dashboard.be.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cosa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String idCosa;
    private String descripcionCosa;

    public Cosa(String idCosa, String descripcionCosa) {
        this.idCosa = idCosa;
        this.descripcionCosa = descripcionCosa;
    }

    public String getIdCosa() {
        return idCosa;
    }

    public void setIdCosa(String idCosa) {
        this.idCosa = idCosa;
    }

    public String getDescripcionCosa() {
        return descripcionCosa;
    }

    public void setDescripcionCosa(String descripcionCosa) {
        this.descripcionCosa = descripcionCosa;
    }

    public Cosa() {
        // Default constructor for JPA
    }

}
