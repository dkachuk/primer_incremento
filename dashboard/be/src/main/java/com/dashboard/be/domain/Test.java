package com.dashboard.be.domain;

public class Test {
    private Integer id;
    private String dato;    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("dato=").append(dato);
        return sb.toString();
    }



}
