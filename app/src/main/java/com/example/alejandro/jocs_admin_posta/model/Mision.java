package com.example.alejandro.jocs_admin_posta.model;

/**
 * Created by Alejandro on 31/5/2016.
 */
public class Mision {

    int id;
    String titulo;
    String descripcion;
    String puntuacion;

    public Mision(String titulo, String descripcion, String puntuacion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
