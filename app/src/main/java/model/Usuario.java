package model;

import java.util.ArrayList;

public class Usuario {
    private String id;
    private String pass;
    private String nombre;
    private String email;

    public Usuario(String id, int rol, String pass, String nombre, String apellidos, String domicilio, String localidad, String provincia, String email){
        this.id = id;
        this.pass = pass;
        this.nombre = nombre;
        this.email = email;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
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
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
