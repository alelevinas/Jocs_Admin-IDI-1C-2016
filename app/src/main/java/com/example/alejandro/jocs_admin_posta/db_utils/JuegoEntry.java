package com.example.alejandro.jocs_admin_posta.db_utils;

import android.provider.BaseColumns;

/**
 * Created by Alejandro on 31/5/2016.
 */
public class JuegoEntry implements BaseColumns {
    public static final String TABLE_NAME = "juegos";
    public static final String COLUMN_KEY_JUEGO_ID = "juego_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_PLATAFORMA = "plataforma";
    public static final String COLUMN_ESTUDIO = "estudio";
    public static final String COLUMN_ANO_PUBLICACION = "ano_publicacion";
    public static final String COLUMN_CURSO = "curso";
    public static final String COLUMN_FOTO_ID = "foto_id";

    //        table create statement
    public static final String CREATE_TABLE_JUEGOS = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_KEY_JUEGO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT," +
            COLUMN_PLATAFORMA + " TEXT," +
            COLUMN_ESTUDIO + " TEXT," +
            COLUMN_ANO_PUBLICACION + " TEXT," +
            COLUMN_CURSO + " TEXT," +
            COLUMN_FOTO_ID + " INTEGER)";
}
