package model;

import java.io.Serializable;
import java.util.List;

public class Universo implements Serializable {
    private String id;
    private String nombre;
    private String descripcion;
    private String creador;
    private String visibilidad;
    private int icono;
    List<String> listaEtiquetas;

    public Universo(String id, String nombre, String descripcion, String creador, String visibilidad, int icono, List<String> listaEtiquetas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
        this.visibilidad = visibilidad;
        this.icono = icono;
        this.listaEtiquetas = listaEtiquetas;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public List<String> getListaEtiquetas() {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(List<String> listaEtiquetas) {
        this.listaEtiquetas = listaEtiquetas;
    }
}
