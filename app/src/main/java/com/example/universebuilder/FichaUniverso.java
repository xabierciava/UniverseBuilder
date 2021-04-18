package com.example.universebuilder;

public class FichaUniverso {
    private String color;
    private String nombre;


    private String descripcion;
    private String id;

    public FichaUniverso(String color, String nombre, String descripcion, String id) {
        this.id= id;
        this.color = color;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}