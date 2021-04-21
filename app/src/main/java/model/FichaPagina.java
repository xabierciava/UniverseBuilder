package model;

import java.util.List;

public class FichaPagina {
    String titulo;
    String id;
    List<String> listaEtiquetas;

    public FichaPagina(String titulo, String id, List<String> listaEtiquetas) {
        this.titulo = titulo;
        this.id = id;
        this.listaEtiquetas = listaEtiquetas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getListaEtiquetas() {
        return listaEtiquetas;
    }

    public void setListaEtiquetas(List<String> listaEtiquetas) {
        this.listaEtiquetas = listaEtiquetas;
    }
}
