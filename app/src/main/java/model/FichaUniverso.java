package model;

public class FichaUniverso {
    private String nombre;
    private int icono;
    private String descripcion;
    private String id;


    public FichaUniverso(int icono, String nombre, String descripcion, String id) {
        this.id= id;
        this.icono = icono;
        this.nombre = nombre;
        this.descripcion = descripcion;
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