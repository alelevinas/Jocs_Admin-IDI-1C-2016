package com.example.alejandro.jocs_admin_posta.db_utils;

import android.provider.BaseColumns;

public class ObjetoEntry implements BaseColumns {
    public static final String TABLE_NAME = "objetos";
    public static final String COLUMN_KEY_OBJETO_ID = "objeto_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_NIVEL = "nivel";
    //referencia a la tabla JUEGOS
    public static final String COLUMN_KEY_JUEGO_ID = "juego_id";

//        public static final String COLUMN_KEY_PERSONAJE (?) = "";


    //        table create statement
    public static final String CREATE_TABLE_OBJETOS = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_KEY_OBJETO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT," +
            //            TODO: QUE NIVEL SEA INT PORQUE SINO SE ORDENA MAL (1 -> 10 -> 2)
            COLUMN_NIVEL + " TEXT," +
            COLUMN_KEY_JUEGO_ID + " INTEGER)";

    public static final String DELETE_OBJETOS_DE_JUEGO = "DELETE FROM " + TABLE_NAME +
            " WHERE " + COLUMN_KEY_JUEGO_ID + " = ?";
    ;
}
