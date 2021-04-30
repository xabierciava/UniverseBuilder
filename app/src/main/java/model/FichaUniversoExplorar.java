package model;

import java.util.List;

public class FichaUniversoExplorar {
    private String nombre;
    private int icono;
    private String descripcion;
    private String id;
    private Boolean favorito;


    public FichaUniversoExplorar(int icono, String nombre, String descripcion, String id, Boolean favorito) {
        this.id= id;
        this.icono = icono;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.favorito = favorito;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
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