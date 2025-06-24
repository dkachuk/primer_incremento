package com.dashboard.be.dto;

public class NuevaCosaPayload {

    private String evento;
    private Long ambienteId;
    private CosaInternaDTO cosa;

    public NuevaCosaPayload(Long ambienteId, CosaInternaDTO cosa) {
        this.evento = "nueva-cosa";
        this.ambienteId = ambienteId;
        this.cosa = cosa;
    }

    public String getEvento() {
        return evento;
    }

    public Long getAmbienteId() {
        return ambienteId;
    }

    public CosaInternaDTO getCosa() {
        return cosa;
    }

    public static class CosaInternaDTO {

        private Long id;
        private String tipo;
        private Boolean estado;

        public CosaInternaDTO(Long id, String tipo, Boolean estado) {
            this.id = id;
            this.tipo = tipo;
            this.estado = estado;
        }

        public Long getId() {
            return id;
        }

        public String getTipo() {
            return tipo;
        }

        public Boolean getEstado() {
            return estado;
        }
    }
}
