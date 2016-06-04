package com.example.alejandro.jocs_admin_posta.model;

/**
 * Created by Alejandro on 31/5/2016.
 */
public class Mision {

    long id;
    String titulo;
    String descripcion;
    String puntuacion;
    long juegoId;

    public Mision(String titulo, String descripcion, String puntuacion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
    }

    public Mision() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setJuegoId(long juegoId) {
        this.juegoId = juegoId;
    }
}
