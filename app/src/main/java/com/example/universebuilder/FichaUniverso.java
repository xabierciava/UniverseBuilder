package com.example.universebuilder;

public class FichaUniverso {
    private String color;
    private String universo;
    private String creador;

    public FichaUniverso(String color, String universo, String creador) {
        this.color = color;
        this.universo = universo;
        this.creador = creador;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUniverso() {
        return universo;
    }

    public void setUniverso(String universo) {
        this.universo = universo;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }
}