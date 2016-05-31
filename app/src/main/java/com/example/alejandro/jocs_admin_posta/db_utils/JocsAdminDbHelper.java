package com.example.alejandro.jocs_admin_posta.db_utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alejandro.jocs_admin_posta.R;
import com.example.alejandro.jocs_admin_posta.model.Juego;
import com.example.alejandro.jocs_admin_posta.model.Personaje;

import java.util.ArrayList;
import java.util.List;


public class JocsAdminDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "JocsAdmin.db";

    public JocsAdminDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(JuegoEntry.CREATE_TABLE_JUEGOS);
            db.execSQL(PersonajeEntry.CREATE_TABLE_PERSONAJES);
            db.execSQL(MisionEntry.CREATE_TABLE_MISIONES);
            db.execSQL(ObjetoEntry.CREATE_TABLE_OBJETOS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.populateDb(db);
    }

    public void populateDb(SQLiteDatabase db) {
        List<Long> ids = this.agregarJuegos(db, 15);
        agregarPersonajes(db, 5, ids);
    }

    private List<Long> agregarJuegos(SQLiteDatabase db, int size) {
        List<Long> l = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            Juego juego = new Juego();
            juego.setNombre("JUEGO_" + i);
            juego.setPlataforma("PLATAFORMA_" + i);
            juego.setEstudio("ESTUDIO_" + i);
            juego.setAno_publicacion("2016");
            juego.setCurso("En curso");
            juego.setFotoId(R.drawable.gta_v);

            l.add(this.agregarJuego(db, juego));
        }
        return l;
    }

    private void agregarPersonajes(SQLiteDatabase db, int size, List<Long> juegos_ids) {
        for (long id : juegos_ids) {
            for (int i = 1; i <= size; i++) {
                this.agregarPersonaje(db, new Personaje("PERSONAJE_" + id + "-" + i, "RAZA_" + id + "-" + i, "NIVEL_" + id + "-" + i), id);
            }
        }
    }



    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + JuegoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PersonajeEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MisionEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ObjetoEntry.TABLE_NAME);
        // create new tables
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public long agregarJuego(SQLiteDatabase db, Juego juego) {
//        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(JuegoEntry.COLUMN_NOMBRE, juego.getNombre());
        values.put(JuegoEntry.COLUMN_PLATAFORMA, juego.getPlataforma());
        values.put(JuegoEntry.COLUMN_ESTUDIO, juego.getEstudio());
        values.put(JuegoEntry.COLUMN_ANO_PUBLICACION, juego.getAno_publicacion());
        values.put(JuegoEntry.COLUMN_CURSO, juego.getCurso());
        values.put(JuegoEntry.COLUMN_FOTO_ID, juego.getFotoId());

        // Insert the new row, returning the primary key value of the new row
        long juego_id = db.insert(JuegoEntry.TABLE_NAME, null, values);

        juego.setId(juego_id);

        return juego_id;
    }

    public long agregarPersonaje(SQLiteDatabase db, Personaje personaje, long juego_id) {
//        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PersonajeEntry.COLUMN_NOMBRE, personaje.getNombre());
        values.put(PersonajeEntry.COLUMN_NIVEL, personaje.getNivel());
        values.put(PersonajeEntry.COLUMN_RAZA, personaje.getRaza());

//        values.put(PersonajeEntry.COLUMN_KEY_JUEGO_ID, personaje.getJuego_id());
        values.put(PersonajeEntry.COLUMN_KEY_JUEGO_ID, juego_id);

        // Insert the new row, returning the primary key value of the new row
        long personaje_id = db.insert(PersonajeEntry.TABLE_NAME, null, values);

        personaje.setId(personaje_id);

        return personaje_id;
    }
}