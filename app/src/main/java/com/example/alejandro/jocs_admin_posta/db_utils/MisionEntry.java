package com.example.alejandro.jocs_admin_posta.db_utils;

import android.provider.BaseColumns;

public class MisionEntry implements BaseColumns {
    public static final String TABLE_NAME = "misiones";
    public static final String COLUMN_KEY_MISION_ID = "mision_id";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_PUNTUACION = "puntuacion";
    //referencia a la tabla JUEGOS
    public static final String COLUMN_KEY_JUEGO_ID = "juego_id";

//        public static final String COLUMN_OBJETOS = "";
//        public static final String COLUMN_MISIONES = "";


    //        table create statement
    public static final String CREATE_TABLE_MISIONES = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_KEY_MISION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TITULO + " TEXT," +
            COLUMN_DESCRIPCION + " TEXT," +
            COLUMN_PUNTUACION + " TEXT," +
            COLUMN_KEY_JUEGO_ID + " INTEGER)";

    public static final String DELETE_MISIONES_DE_JUEGO = "DELETE FROM " + TABLE_NAME +
            " WHERE " + COLUMN_KEY_JUEGO_ID + " = ?";
    ;
}
