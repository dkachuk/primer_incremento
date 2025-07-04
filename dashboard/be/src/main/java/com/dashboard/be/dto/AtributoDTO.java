package com.dashboard.be.dto;

public class AtributoDTO {

    private String nombre;
    private String valor;

    public AtributoDTO() {
    }

    public AtributoDTO(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
