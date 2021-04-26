package model;

import java.util.List;

public class Pagina {
    private String id;
    private String titulo;
    private String universo;
    private String contenido;
    private List<String> categoria;


    public Pagina(String id, String titulo, String universo, String contenido, List<String> categoria) {
        this.id = id;
        this.titulo = titulo;
        this.universo = universo;
        this.contenido = contenido;
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<String> categoria) {
        this.categoria = categoria;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniverso() {
        return universo;
    }

    public void setUniverso(String universo) {
        this.universo = universo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

}
