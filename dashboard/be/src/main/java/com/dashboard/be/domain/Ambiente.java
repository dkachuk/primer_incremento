package com.dashboard.be.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ambiente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idAmbiente;
    private String descripcionAmbiente;

    public Ambiente(int idAmbiente, String descripcionAmbiente) {
        this.idAmbiente = idAmbiente;
        this.descripcionAmbiente = descripcionAmbiente;
    }

    public String getDescripcionAmbiente() {
        return descripcionAmbiente;
    }

    public int getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(int idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public void setDescripcionAmbiente(String descripcionAmbiente) {
        this.descripcionAmbiente = descripcionAmbiente;
    }

    public Ambiente() {
        // Default constructor for JPA
    }

}
