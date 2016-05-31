package com.example.alejandro.jocs_admin_posta.model;

/**
 * Created by Alejandro on 31/5/2016.
 */
public class Personaje {
    long id;
    String nombre;
    String raza;
    String nivel;
    long juego_id;

    public Personaje() {

    }

    public Personaje(String nombre, String raza, String nivel) {
        this.nombre = nombre;
        this.raza = raza;
        this.nivel = nivel;
    }

    public long getJuego_id() {
        return juego_id;
    }

    public void setJuego_id(long juego_id) {
        this.juego_id = juego_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
