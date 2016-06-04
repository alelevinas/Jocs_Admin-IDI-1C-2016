package com.example.alejandro.jocs_admin_posta.db_utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.alejandro.jocs_admin_posta.R;
import com.example.alejandro.jocs_admin_posta.model.Juego;
import com.example.alejandro.jocs_admin_posta.model.Mision;
import com.example.alejandro.jocs_admin_posta.model.Objeto;
import com.example.alejandro.jocs_admin_posta.model.Personaje;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 31/5/2016.
 */
public class DatabaseManager {
    // Logcat tag
    private static final String LOG = DatabaseManager.class.getName();
    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private Integer mOpenCounter = 0;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }
    // ------------------------ "juegos" table methods ----------------//

    public void restart() {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + JuegoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PersonajeEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MisionEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ObjetoEntry.TABLE_NAME);
        mDatabaseHelper.onCreate(db);
    }

    // ------------------------ "juegos" table methods ----------------//

    public long agregarJuego(Juego juego) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        return agregarJuego(db, juego);
    }

    public long agregarJuego(SQLiteDatabase db, Juego juego) {
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


    /**
     * get single juego
     */
    public Juego getJuego(long juego_id) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + JuegoEntry.TABLE_NAME + " WHERE "
                + JuegoEntry.COLUMN_KEY_JUEGO_ID + " = " + juego_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Juego juego = new Juego();
        juego.setId(c.getLong(c.getColumnIndex(JuegoEntry.COLUMN_KEY_JUEGO_ID)));
        juego.setNombre(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_NOMBRE)));
        juego.setPlataforma(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_PLATAFORMA)));
        juego.setEstudio(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_ESTUDIO)));
        juego.setAno_publicacion(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_ANO_PUBLICACION)));
        juego.setCurso(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_CURSO)));
        juego.setFotoId(c.getInt(c.getColumnIndex(JuegoEntry.COLUMN_FOTO_ID)));

        return juego;
    }

    /**
     * getting all juegos
     */
    public List<Juego> getAllJuegos() {
        List<Juego> juegos = new ArrayList<Juego>();
        String selectQuery = "SELECT  * FROM " + JuegoEntry.TABLE_NAME;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Juego juego = new Juego();
                juego.setId(c.getLong(c.getColumnIndex(JuegoEntry.COLUMN_KEY_JUEGO_ID)));
                juego.setNombre(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_NOMBRE)));
                juego.setPlataforma(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_PLATAFORMA)));
                juego.setEstudio(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_ESTUDIO)));
                juego.setAno_publicacion(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_ANO_PUBLICACION)));
                juego.setCurso(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_CURSO)));
                juego.setFotoId(c.getInt(c.getColumnIndex(JuegoEntry.COLUMN_FOTO_ID)));

                // adding to juego list
                juegos.add(juego);
            } while (c.moveToNext());
        }
        return juegos;
    }


    // ------------------------ "Personajes" table methods ----------------//

    public long agregarPersonaje(Personaje personaje, long juego_id) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        return agregarPersonaje(db, personaje, juego_id);
    }

    public long agregarPersonaje(SQLiteDatabase db, Personaje personaje, long juego_id) {
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


    /**
     * getting all personajes
     */
    public List<Personaje> getAllPersonajes(String selectQuery) {
        List<Personaje> personajes = new ArrayList<Personaje>();

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list


        if (c.moveToFirst()) {
            do {
                Personaje personaje = new Personaje();
                personaje.setId(c.getLong(c.getColumnIndex(PersonajeEntry.COLUMN_KEY_PERSONAJE_ID)));
                personaje.setNombre(c.getString(c.getColumnIndex(PersonajeEntry.COLUMN_NOMBRE)));
                personaje.setNivel(c.getString(c.getColumnIndex(PersonajeEntry.COLUMN_NIVEL)));
                personaje.setRaza(c.getString(c.getColumnIndex(PersonajeEntry.COLUMN_RAZA)));
                personaje.setJuego_id(c.getLong(c.getColumnIndex(PersonajeEntry.COLUMN_KEY_JUEGO_ID)));

                // adding to personaje list
                personajes.add(personaje);
            } while (c.moveToNext());
        }
        return personajes;
    }

    public List<Personaje> getAllPersonajesFromJuego(long id) {
        return this.getAllPersonajes("SELECT * FROM " + PersonajeEntry.TABLE_NAME +
                " WHERE " + PersonajeEntry.COLUMN_KEY_JUEGO_ID + " = " + id +
                " ORDER BY " + PersonajeEntry.COLUMN_NIVEL);
    }

// ------------------------ "Objetos" table methods ----------------//

    public long agregarObjeto(Objeto objeto, long juego_id) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        return agregarObjeto(db, objeto, juego_id);
    }

    public long agregarObjeto(SQLiteDatabase db, Objeto objeto, long juego_id) {
        ContentValues values = new ContentValues();
        values.put(ObjetoEntry.COLUMN_NOMBRE, objeto.getNombre());
        values.put(ObjetoEntry.COLUMN_NIVEL, objeto.getNivel());

//        values.put(ObjetoEntry.COLUMN_KEY_JUEGO_ID, objeto.getJuego_id());
        values.put(ObjetoEntry.COLUMN_KEY_JUEGO_ID, juego_id);

        // Insert the new row, returning the primary key value of the new row
        long objeto_id = db.insert(ObjetoEntry.TABLE_NAME, null, values);

        objeto.setId(objeto_id);

        return objeto_id;
    }


    /**
     * getting all objetos
     */
    public List<Objeto> getAllObjetos(String selectQuery) {
        List<Objeto> objetos = new ArrayList<Objeto>();

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (c.moveToFirst()) {
            do {
                Objeto objeto = new Objeto();
                objeto.setId(c.getLong(c.getColumnIndex(ObjetoEntry.COLUMN_KEY_OBJETO_ID)));
                objeto.setNombre(c.getString(c.getColumnIndex(ObjetoEntry.COLUMN_NOMBRE)));
                objeto.setNivel(c.getString(c.getColumnIndex(ObjetoEntry.COLUMN_NIVEL)));
                objeto.setJuego_id(c.getLong(c.getColumnIndex(ObjetoEntry.COLUMN_KEY_JUEGO_ID)));

                // adding to objeto list
                objetos.add(objeto);
            } while (c.moveToNext());
        }
        return objetos;
    }

    public List<Objeto> getAllObjetosFromJuego(long id) {
        return this.getAllObjetos("SELECT  * FROM " + ObjetoEntry.TABLE_NAME +
                " WHERE " + ObjetoEntry.COLUMN_KEY_JUEGO_ID + " = " + id +
                " ORDER BY " + ObjetoEntry.COLUMN_NIVEL);
    }


    // ------------------------ "Misions" table methods ----------------//

    public long agregarMision(Mision mision, long juego_id) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        return agregarMision(db, mision, juego_id);
    }

    public long agregarMision(SQLiteDatabase db, Mision mision, long juego_id) {
        ContentValues values = new ContentValues();
        values.put(MisionEntry.COLUMN_TITULO, mision.getTitulo());
        values.put(MisionEntry.COLUMN_DESCRIPCION, mision.getDescripcion());
        values.put(MisionEntry.COLUMN_PUNTUACION, mision.getPuntuacion());

//        values.put(MisionEntry.COLUMN_KEY_JUEGO_ID, mision.getJuego_id());
        values.put(MisionEntry.COLUMN_KEY_JUEGO_ID, juego_id);

        // Insert the new row, returning the primary key value of the new row
        long mision_id = db.insert(MisionEntry.TABLE_NAME, null, values);

        mision.setId(mision_id);

        return mision_id;
    }


    /**
     * getting all misions
     */
    public List<Mision> getAllMisiones(String selectQuery) {
        List<Mision> misiones = new ArrayList<Mision>();

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (c.moveToFirst()) {
            do {
                Mision mision = new Mision();
                mision.setId(c.getLong(c.getColumnIndex(MisionEntry.COLUMN_KEY_MISION_ID)));
                mision.setTitulo(c.getString(c.getColumnIndex(MisionEntry.COLUMN_TITULO)));
                mision.setDescripcion(c.getString(c.getColumnIndex(MisionEntry.COLUMN_DESCRIPCION)));
                mision.setPuntuacion(c.getString(c.getColumnIndex(MisionEntry.COLUMN_PUNTUACION)));
                mision.setJuegoId(c.getLong(c.getColumnIndex(MisionEntry.COLUMN_KEY_JUEGO_ID)));

                // adding to mision list
                misiones.add(mision);
            } while (c.moveToNext());
        }
        return misiones;
    }

    public List<Mision> getAllMisionesFromJuego(long id) {
        return this.getAllMisiones("SELECT  * FROM " + MisionEntry.TABLE_NAME +
                " WHERE " + MisionEntry.COLUMN_KEY_JUEGO_ID + " = " + id);
    }

    //    ------------------------------- PA POPULAR --------------------------
    public void populateDb(SQLiteDatabase db) {
        List<Long> ids = this.agregarJuegos(db, 15);
        agregarPersonajes(db, 25, ids);
        agregarObjetos(db, 25, ids);
        agregarMisiones(db, 25, ids);
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

    private void agregarObjetos(SQLiteDatabase db, int size, List<Long> juegos_ids) {
        for (long id : juegos_ids) {
            for (int i = 1; i <= size; i++) {
                this.agregarObjeto(db, new Objeto("OBJETO_" + id + "-" + i, "NIVEL_" + id + "-" + i), id);
            }
        }
    }

    private void agregarMisiones(SQLiteDatabase db, int size, List<Long> juegos_ids) {
        for (long id : juegos_ids) {
            for (int i = 1; i <= size; i++) {
                this.agregarMision(db, new Mision("MISION_" + id + "-" + i, "Descripcion que suele ser un poco mas larga_" + id + "-" + i, "PUNTUACION PERFECTA WACHOOO"), id);
            }
        }
    }



}
