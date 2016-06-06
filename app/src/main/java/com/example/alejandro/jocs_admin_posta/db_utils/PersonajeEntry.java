package com.example.alejandro.jocs_admin_posta.db_utils;

import android.provider.BaseColumns;

public class PersonajeEntry implements BaseColumns {
    public static final String TABLE_NAME = "personajes";
    public static final String COLUMN_KEY_PERSONAJE_ID = "personaje_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_RAZA = "raza";
    public static final String COLUMN_NIVEL = "nivel";
    public static final String COLUMN_FOTO_ID = "foto_id";
    //referencia a la tabla JUEGOS
    public static final String COLUMN_KEY_JUEGO_ID = "juego_id";


    //        table create statement
    public static final String CREATE_TABLE_PERSONAJES = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_KEY_PERSONAJE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT," +
            COLUMN_RAZA + " TEXT," +
//            TODO: QUE NIVEL SEA INT PORQUE SINO SE ORDENA MAL (1 -> 10 -> 2)
            COLUMN_NIVEL + " TEXT," +
            COLUMN_FOTO_ID + " INTEGER, " +
            COLUMN_KEY_JUEGO_ID + " INTEGER)";

    public static final String DELETE_PERSONAJES_DE_JUEGO = "DELETE FROM " + TABLE_NAME +
            " WHERE " + COLUMN_KEY_JUEGO_ID + " = ?";

    public static final String DELETE_PERSONAJE = "DELETE FROM " + TABLE_NAME +
            " WHERE " + COLUMN_KEY_PERSONAJE_ID + " = ?";
    ;
}
