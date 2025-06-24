package com.dashboard.be.dto;

public class CambioEstadoPayload {

    private String evento;
    private Long cosaId;
    private Boolean nuevoEstado;

    public CambioEstadoPayload() {
        this.evento = "cambiar-estado";
    }

    public CambioEstadoPayload(Long cosaId, Boolean nuevoEstado) {
        this.evento = "cambiar-estado";
        this.cosaId = cosaId;
        this.nuevoEstado = nuevoEstado;
    }

    public String getEvento() {
        return evento;
    }

    public Long getCosaId() {
        return cosaId;
    }

    public Boolean getNuevoEstado() {
        return nuevoEstado;
    }
}
