package com.example.alejandro.jocs_admin_posta.model;

/**
 * Created by Alejandro on 31/5/2016.
 */
public class Personaje {
    int id;
    String nombre;
    String raza;
    String nivel;

    public Personaje(String nombre, String raza, String nivel) {
        this.nombre = nombre;
        this.raza = raza;
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

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
