package com.example.alejandro.jocs_admin_posta.model;

/**
 * Created by Alejandro on 31/5/2016.
 */
public class Juego {
    public static final String NAME_PREFIX = "JUEGO_";
    public static final String SURNAME_PREFIX = "ESTUDIO_";
    public static final String EMAIL_PREFIX = "PLATAFORMA_";
    //    List<Personaje> personajes;    ALGO CON LA BASE DE DATOS
//    Objeto objetos;
//    Mision missiones;
    protected int fotoId;
    protected String ano_publicacion;
    protected String curso;
    String nombre;
    String plataforma;
    String estudio;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public int getFotoId() {
        return fotoId;
    }

    public void setFotoId(int fotoId) {
        this.fotoId = fotoId;
    }

    public String getAno_publicacion() {
        return ano_publicacion;
    }

    public void setAno_publicacion(String ano_publicacion) {
        this.ano_publicacion = ano_publicacion;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}