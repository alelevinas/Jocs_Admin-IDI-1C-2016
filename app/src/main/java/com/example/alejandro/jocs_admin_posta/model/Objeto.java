package com.example.alejandro.jocs_admin_posta.model;

/**
 * Created by Alejandro on 31/5/2016.
 */
public class Objeto {

    int id;
    String nombre;
    String nivel;

    public Objeto(String nombre, String nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
