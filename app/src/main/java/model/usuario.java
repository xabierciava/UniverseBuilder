package model;

import java.util.ArrayList;

public class usuario {
    private String id_usuario;
    private String pass;
    private String nombre;
    private String email;

    public usuario(String id_usuario, int rol, String pass, String nombre, String apellidos, String domicilio, String localidad, String provincia, String email){
        this.id_usuario = id_usuario;
        this.pass = pass;
        this.nombre = nombre;
        this.email = email;
    }

    public String getId(){
        return id_usuario;
    }
    public void setId(String id_usuario){
        this.id_usuario = id_usuario;
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
