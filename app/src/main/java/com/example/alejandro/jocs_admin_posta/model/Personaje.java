package com.example.alejandro.jocs_admin_posta.model;

import com.example.alejandro.jocs_admin_posta.R;

/**
 * Created by Alejandro on 31/5/2016.
 */
public class Personaje {
    long id;
    String nombre;
    String raza;
    String nivel;
    int fotoId;
    long juego_id;

    public Personaje(String nombre, String raza, String nivel, int fotoID) {
        this.nombre = nombre;
        this.raza = raza;
        this.nivel = nivel;
        this.fotoId = fotoID;
    }

    public Personaje() {

    }

    public Personaje(String nombre, String raza, String nivel) {
        this.nombre = nombre;
        this.raza = raza;
        this.nivel = nivel;
        this.fotoId = R.drawable.ic_android;
    }

    public int getFotoId() {
        return fotoId;
    }

    public void setFotoId(int fotoId) {
        this.fotoId = fotoId;
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
