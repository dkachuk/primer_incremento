package com.dashboard.be.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class PlantillaCosa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "plantilla", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PlantillaAtributo> atributos = new ArrayList<>();

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<PlantillaAtributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<PlantillaAtributo> atributos) {
        this.atributos = atributos;
    }
}
