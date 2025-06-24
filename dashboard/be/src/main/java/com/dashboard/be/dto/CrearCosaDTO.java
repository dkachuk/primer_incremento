package com.dashboard.be.dto;

import java.util.List;

public class CrearCosaDTO {

    private String type;
    private Boolean state;
    private Long ambienteId;
    private List<AtributoDTO> atributos;

    // Getters y setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Long getAmbienteId() {
        return ambienteId;
    }

    public void setAmbienteId(Long ambienteId) {
        this.ambienteId = ambienteId;
    }

    public List<AtributoDTO> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<AtributoDTO> atributos) {
        this.atributos = atributos;
    }
}
