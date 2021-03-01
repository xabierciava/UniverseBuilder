package model;

import java.util.ArrayList;

public class usuario {
    private String id_usuario;
    private int rol;
    private String pass;
    private String nombre;
    private String apellidos;
    private String domicilio;
    private String localidad;
    private String provincia;
    private String email;

    public usuario(String id_usuario, int rol, String pass, String nombre, String apellidos, String domicilio, String localidad, String provincia, String email){
        this.id_usuario = id_usuario;
        this.rol = rol;
        this.pass = pass;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.provincia = provincia;
        this.email = email;
    }

    public String getId(){
        return id_usuario;
    }
    public void setId(String id_usuario){
        this.id_usuario = id_usuario;
    }
    public int getRol(){
        return rol;
    }
    public void setRol(int rol){
        this.rol = rol;
    }
    public String getPass(){
        return pass;
    }
    public void setPass(String pass){
        this.pass = pass;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getApellidos(){
        return apellidos;
    }
    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }
    public String getDomicilio(){
        return domicilio;
    }
    public void setDomicilio(String domicilio){
        this.domicilio = domicilio;
    }
    public String getLocalidad(){
        return localidad;
    }
    public void setLocalidad(String localidad){
        this.localidad = localidad;
    }
    public String getProvincia(){
        return provincia;
    }
    public void setProvincia(String provincia){
        this.provincia = provincia;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
