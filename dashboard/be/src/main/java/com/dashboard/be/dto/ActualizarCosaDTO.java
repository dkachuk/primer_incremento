package com.dashboard.be.dto;

import java.util.List;

public class ActualizarCosaDTO {

    private Boolean state;
    private List<AtributoDTO> atributos;

    public ActualizarCosaDTO() {
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public List<AtributoDTO> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<AtributoDTO> atributos) {
        this.atributos = atributos;
    }
}
