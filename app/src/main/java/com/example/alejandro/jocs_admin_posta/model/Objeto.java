package com.example.alejandro.jocs_admin_posta.model;

/**
 * Created by Alejandro on 31/5/2016.
 */
public class Objeto {

    long id;
    String nombre;
    String nivel;
    private long juego_id;

    public Objeto(String nombre, String nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public Objeto() {

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

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setJuego_id(long juego_id) {
        this.juego_id = juego_id;
    }
}
