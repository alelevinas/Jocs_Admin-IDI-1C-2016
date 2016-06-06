package com.example.alejandro.jocs_admin_posta.db_utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.alejandro.jocs_admin_posta.R;
import com.example.alejandro.jocs_admin_posta.model.Juego;
import com.example.alejandro.jocs_admin_posta.model.Mision;
import com.example.alejandro.jocs_admin_posta.model.Objeto;
import com.example.alejandro.jocs_admin_posta.model.Personaje;

import java.io.ByteArrayOutputStream;
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
    private static Context context;
    private Integer mOpenCounter = 0;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper, Context cont) {
        if (instance == null) {
            instance = new DatabaseManager();
            context = cont;
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
//        mDatabaseHelper.onUpgrade(db,1,2);
    }

    // ------------------------ "juegos" table methods ----------------//

    public long agregarJuego(Juego juego) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        return agregarJuego(db, juego);
    }

    public long agregarJuego(SQLiteDatabase db, Juego juego) {
//        ContentValues values = new ContentValues();
//        values.put(JuegoEntry.COLUMN_NOMBRE, juego.getNombre());
//        values.put(JuegoEntry.COLUMN_PLATAFORMA, juego.getPlataforma());
//        values.put(JuegoEntry.COLUMN_ESTUDIO, juego.getEstudio());
//        values.put(JuegoEntry.COLUMN_ANO_PUBLICACION, juego.getAno_publicacion());
//        values.put(JuegoEntry.COLUMN_CURSO, juego.getCurso());
//        values.put(JuegoEntry.COLUMN_FOTO_ID, juego.getFotoId());
//        values.put(JuegoEntry.COLUMN_FOTO, juego.getLaFoto());

        String sql = JuegoEntry.INSERT_JUEGO;
        SQLiteStatement insertStmt = db.compileStatement(sql);
        insertStmt.clearBindings();
        insertStmt.bindString(1, juego.getNombre());
        insertStmt.bindString(2, juego.getPlataforma());
        insertStmt.bindString(3, juego.getEstudio());
        insertStmt.bindString(4, juego.getAno_publicacion());
        insertStmt.bindString(5, juego.getCurso());
        insertStmt.bindLong(6, juego.getFotoId());
        insertStmt.bindBlob(7, juego.getLaFoto());
        long juego_id = insertStmt.executeInsert();



        // Insert the new row, returning the primary key value of the new row
//        long juego_id = db.insert(JuegoEntry.TABLE_NAME, null, values);

        juego.setId(juego_id);

        Log.e("DATABASE MANAGER", "JUEGO CON AGREGADO CON id: " + juego_id);
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
        if (c.moveToFirst()) {
            juego.setId(c.getLong(c.getColumnIndex(JuegoEntry.COLUMN_KEY_JUEGO_ID)));
            juego.setNombre(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_NOMBRE)));
            juego.setPlataforma(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_PLATAFORMA)));
            juego.setEstudio(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_ESTUDIO)));
            juego.setAno_publicacion(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_ANO_PUBLICACION)));
            juego.setCurso(c.getString(c.getColumnIndex(JuegoEntry.COLUMN_CURSO)));
            juego.setFotoId(c.getInt(c.getColumnIndex(JuegoEntry.COLUMN_FOTO_ID)));
        }

        return juego;
    }

    /**
     * getting all juegos
     */
    public List<Juego> getAllJuegos() {
        List<Juego> juegos = new ArrayList<>();
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

    public void updateJuego(long juego_id, String mTituloText, String mPlataformaText,
                            String mEstudioText, String mAnoPublicacionText, String mEstadoText,
                            byte[] mImagen) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(JuegoEntry.COLUMN_NOMBRE, mTituloText);
        values.put(JuegoEntry.COLUMN_PLATAFORMA, mPlataformaText);
        values.put(JuegoEntry.COLUMN_ESTUDIO, mEstudioText);
        values.put(JuegoEntry.COLUMN_ANO_PUBLICACION, mAnoPublicacionText);
        values.put(JuegoEntry.COLUMN_CURSO, mEstadoText);
//        values.put(JuegoEntry.COLUMN_FOTO_ID, m);
        values.put(JuegoEntry.COLUMN_FOTO, mImagen);


        db.update(JuegoEntry.TABLE_NAME, values, JuegoEntry.COLUMN_KEY_JUEGO_ID + " = " + Long.toString(juego_id), null);
    }

    public void eliminarJuego(long id) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        this.eliminarPersonajesDe(db, id);
        this.eliminarObjetosDe(db, id);
        this.eliminarMisionesDe(db, id);

        String sql = JuegoEntry.DELETE_JUEGO;
        SQLiteStatement deleteStmt = db.compileStatement(sql);
        deleteStmt.clearBindings();
        deleteStmt.bindString(1, "" + id);
        deleteStmt.executeUpdateDelete();
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
        values.put(JuegoEntry.COLUMN_FOTO_ID, personaje.getFotoId());

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
                personaje.setFotoId(c.getInt(c.getColumnIndex(PersonajeEntry.COLUMN_FOTO_ID)));
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

    public Personaje getPersonaje(long id) {
        String selectQuery = "SELECT  * FROM " + PersonajeEntry.TABLE_NAME + " WHERE "
                + PersonajeEntry.COLUMN_KEY_PERSONAJE_ID + " = " + id;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        Personaje personaje = new Personaje();
        if (c.moveToFirst()) {
            do {
                personaje.setId(c.getLong(c.getColumnIndex(PersonajeEntry.COLUMN_KEY_PERSONAJE_ID)));
                personaje.setNombre(c.getString(c.getColumnIndex(PersonajeEntry.COLUMN_NOMBRE)));
                personaje.setNivel(c.getString(c.getColumnIndex(PersonajeEntry.COLUMN_NIVEL)));
                personaje.setRaza(c.getString(c.getColumnIndex(PersonajeEntry.COLUMN_RAZA)));
                personaje.setFotoId(c.getInt(c.getColumnIndex(PersonajeEntry.COLUMN_FOTO_ID)));
                personaje.setJuego_id(c.getLong(c.getColumnIndex(PersonajeEntry.COLUMN_KEY_JUEGO_ID)));

            } while (c.moveToNext());
        }
        return personaje;
    }



    private void eliminarPersonajesDe(SQLiteDatabase db, long id) {
        String sql = PersonajeEntry.DELETE_PERSONAJES_DE_JUEGO;
        SQLiteStatement deleteStmt = db.compileStatement(sql);
        deleteStmt.clearBindings();
        deleteStmt.bindString(1, "" + id);
        deleteStmt.executeUpdateDelete();
    }

    public void updatePersonaje(long personaje_id, String nombre, String raza, String nivel, byte[] bitmapdata_imagen) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PersonajeEntry.COLUMN_NOMBRE, nombre);
        values.put(PersonajeEntry.COLUMN_RAZA, raza);
        values.put(PersonajeEntry.COLUMN_NIVEL, nivel);
        //        values.put(PersonajeEntry.COLUMN_FOTO_ID, m);
//        values.put(PersonajeEntry.COLUMN_FOTO_ID, mImagen);


        db.update(PersonajeEntry.TABLE_NAME, values, PersonajeEntry.COLUMN_KEY_PERSONAJE_ID + " = " + personaje_id, null);
    }

    public void eliminarPersonaje(long personaje_id) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        String sql = PersonajeEntry.DELETE_PERSONAJE;
        SQLiteStatement deleteStmt = db.compileStatement(sql);
        deleteStmt.clearBindings();
        deleteStmt.bindString(1, "" + personaje_id);
        deleteStmt.executeUpdateDelete();
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

        Log.e("DATABASE MANAGER", "AGREGANDO OBJETO " + objeto.getNombre() + " en JUEGO: " + juego_id);

        return objeto_id;
    }

    public Objeto getObjeto(long id) {
        String selectQuery = "SELECT  * FROM " + ObjetoEntry.TABLE_NAME +
                " WHERE " + ObjetoEntry.COLUMN_KEY_OBJETO_ID + " = " + id;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        Objeto objeto = new Objeto();
        if (c.moveToFirst()) {
            do {
                objeto.setId(c.getLong(c.getColumnIndex(ObjetoEntry.COLUMN_KEY_OBJETO_ID)));
                objeto.setNombre(c.getString(c.getColumnIndex(ObjetoEntry.COLUMN_NOMBRE)));
                objeto.setNivel(c.getString(c.getColumnIndex(ObjetoEntry.COLUMN_NIVEL)));
                objeto.setJuego_id(c.getLong(c.getColumnIndex(ObjetoEntry.COLUMN_KEY_JUEGO_ID)));
            } while (c.moveToNext());
        }
        return objeto;
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

    private void eliminarObjetosDe(SQLiteDatabase db, long id) {
        String sql = ObjetoEntry.DELETE_OBJETOS_DE_JUEGO;
        SQLiteStatement deleteStmt = db.compileStatement(sql);
        deleteStmt.clearBindings();
        deleteStmt.bindString(1, "" + id);
        deleteStmt.executeUpdateDelete();
    }

    public void updateObjeto(long objeto_id, String nombre, String nivel) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ObjetoEntry.COLUMN_NOMBRE, nombre);
        values.put(ObjetoEntry.COLUMN_NIVEL, nivel);

        db.update(ObjetoEntry.TABLE_NAME, values, ObjetoEntry.COLUMN_KEY_OBJETO_ID + " = " + objeto_id, null);
    }

    public void eliminarObjeto(long objeto_id) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        String sql = ObjetoEntry.DELETE_OBJETO;
        SQLiteStatement deleteStmt = db.compileStatement(sql);
        deleteStmt.clearBindings();
        deleteStmt.bindString(1, "" + objeto_id);
        deleteStmt.executeUpdateDelete();
    }


    // ------------------------ "Misiones" table methods ----------------//

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

    public Mision getMision(long id) {
        String selectQuery = "SELECT  * FROM " + MisionEntry.TABLE_NAME +
                " WHERE " + MisionEntry.COLUMN_KEY_MISION_ID + " = " + id;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through row and adding to list
        Mision mision = new Mision();
        if (c.moveToFirst()) {
            do {
                mision.setId(c.getLong(c.getColumnIndex(MisionEntry.COLUMN_KEY_MISION_ID)));
                mision.setTitulo(c.getString(c.getColumnIndex(MisionEntry.COLUMN_TITULO)));
                mision.setDescripcion(c.getString(c.getColumnIndex(MisionEntry.COLUMN_DESCRIPCION)));
                mision.setPuntuacion(c.getString(c.getColumnIndex(MisionEntry.COLUMN_PUNTUACION)));
                mision.setJuegoId(c.getLong(c.getColumnIndex(MisionEntry.COLUMN_KEY_JUEGO_ID)));
            } while (c.moveToNext());
        }
        return mision;
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

    private void eliminarMisionesDe(SQLiteDatabase db, long id) {
        String sql = MisionEntry.DELETE_MISIONES_DE_JUEGO;
        SQLiteStatement deleteStmt = db.compileStatement(sql);
        deleteStmt.clearBindings();
        deleteStmt.bindString(1, "" + id);
        deleteStmt.executeUpdateDelete();
    }

    public void eliminarMision(long mision_id) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        String sql = MisionEntry.DELETE_MISION;
        SQLiteStatement deleteStmt = db.compileStatement(sql);
        deleteStmt.clearBindings();
        deleteStmt.bindString(1, "" + mision_id);
        deleteStmt.executeUpdateDelete();
    }

    public void updateMision(long mision_id, String titulo, String mDescripcionText, String puntuacion) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MisionEntry.COLUMN_TITULO, titulo);
        values.put(MisionEntry.COLUMN_DESCRIPCION, mDescripcionText);
        values.put(MisionEntry.COLUMN_PUNTUACION, puntuacion);

        db.update(MisionEntry.TABLE_NAME, values, MisionEntry.COLUMN_KEY_MISION_ID + " = " + mision_id, null);
    }

    //    ------------------------------- PA POPULAR --------------------------
    public void populateDb(SQLiteDatabase db) {
        agregarSuperMario64(db);
        agregarLegendOfZeldaOcarinaOfTime(db);


        List<Long> ids = this.agregarJuegos(db, 10);
        agregarPersonajes(db, 7, ids);
        agregarObjetos(db, 8, ids);
        agregarMisiones(db, 9, ids);
    }

    private void agregarLegendOfZeldaOcarinaOfTime(SQLiteDatabase db) {
        Juego zelda = new Juego();
        zelda.setNombre("The Legend of Zelda: Ocarina of Time");
        zelda.setPlataforma("Nintendo 64");
        zelda.setEstudio("Nintendo");
        zelda.setAno_publicacion("1998");
        zelda.setCurso("No Iniciado");
        zelda.setFotoId(R.drawable.zelda_the_legend_of_zelda_ocarina_of_time);

        Bitmap bMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.zelda_the_legend_of_zelda_ocarina_of_time);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bMap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        zelda.setLaFoto(bitmapdata);


        long id = this.agregarJuego(db, zelda);

        this.agregarPersonaje(db, new Personaje("Link", "Hyliano", "1", R.drawable.zelda_link_chico), id);
        this.agregarPersonaje(db, new Personaje("Princesa Zelda", "Hyliano", "1", R.drawable.zelda_zelda_chico), id);
        this.agregarPersonaje(db, new Personaje("Ganondorf", "Gerudo", "1", R.drawable.zelda_ganondorf_chico), id);
        this.agregarPersonaje(db, new Personaje("Nadi", "Hada", "1", R.drawable.zelda_nadi), id);
        this.agregarPersonaje(db, new Personaje("Sheik", "Sheikah", "1", R.drawable.zelda_sheik_chico), id);

        this.agregarObjeto(db, new Objeto("Kokiri Sword", "1"), id);
        this.agregarObjeto(db, new Objeto("Master Sword", "1"), id);
        this.agregarObjeto(db, new Objeto("Giant's Knife", "1"), id);
        this.agregarObjeto(db, new Objeto("Deku Nuts", "1"), id);
        this.agregarObjeto(db, new Objeto("Fire Arrows", "1"), id);
        this.agregarObjeto(db, new Objeto("Ice Arrows", "1"), id);
        this.agregarObjeto(db, new Objeto("Quiver", "1"), id);
        this.agregarObjeto(db, new Objeto("Deku Shield", "1"), id);
        this.agregarObjeto(db, new Objeto("Kokiri Boots", "1"), id);
        this.agregarObjeto(db, new Objeto("Kokiri Tunic", "1"), id);
        this.agregarObjeto(db, new Objeto("Ocarina of Time", "1"), id);


        this.agregarMision(db, new Mision("Interior del Gran Árbol Deku", "El Interior del Gran Árbol Deku (Inside the Deku Tree en inglés) es la primera mazmorra en The Legend of Zelda: Ocarina of Time y por lo tanto la primera mazmorra en 3D de la saga de Zelda. Esta mazmorra continúa una tendencia en los juegos de la saga, ya que es la primera mazmorra con una temática forestal. También es la segunda mazmorra que tendrá lugar dentro de un árbol, la primera es una mazmorra de The Legend of Zelda. Sin embargo, esta mazmorra era presumiblemente dentro árbol muerto, por lo que estamos hablando de la primera mazmorra que tiene lugar en un ser vivo.", "0%"), id);
        this.agregarMision(db, new Mision("Princesa del Destino", "Después de derrotar al jefe y entrar en la luz, aparecerás de nuevo fuera, sólo para oír una conferencia del Árbol Deku cuando finalmente revela lo que le sucedió. Un hombre malvado del desierto exigió al Árbol Deku una de las tres piedras espirituales, que al juntarse, pueden abrir una puerta al Reino Sagrado. Una vez allí, el hombre malvado sería capaz de obtener la Trifuerza, un objeto de poder omnipotente dejado por las tres Diosas de Oro que crearon la tierra de Hyrule.\n" +
                "\n" + "Después de su épica historia, el Árbol Deku insiste en que el hombre malvado del desierto debe ser detenido y nunca permitirle entrar al Reino Sagrado. Debido a que el Árbol Deku no le dio la piedra espiritual del bosque, la Esmeralda Kokiri, el malvado hombre lanzó una maldición sobre él, que acabaría con su vida, hiciese lo que se hiciese. Antes de morir, insta a Link a buscar a la \"Princesa del Destino\" en el Castillo de Hyrule y te da la piedra que el hombre buscaba. El Gran Árbol Deku, el guardián de los Kokiri, muere...", "0%"), id);
        this.agregarMision(db, new Mision("Canción del Sol", "Busqueda del cofre pequeño que contiene el Escudo Hylian.", "0%"), id);
        this.agregarMision(db, new Mision("Canción de Epona", "Busqueda en el Rancho Lon Lon de una Pieza de Corazon", "0%"), id);

    }

    void agregarSuperMario64(SQLiteDatabase db) {
        Juego SuperMario = new Juego();
        SuperMario.setNombre("Super Mario 64");
        SuperMario.setPlataforma("Nintendo 64");
        SuperMario.setEstudio("Nintendo");
        SuperMario.setAno_publicacion("1996");
        SuperMario.setCurso("Iniciado");
        SuperMario.setFotoId(R.drawable.mario_super_mario_64);

        Bitmap bMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mario_super_mario_64);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bMap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        SuperMario.setLaFoto(bitmapdata);

        long id = this.agregarJuego(db, SuperMario);

        this.agregarPersonaje(db, new Personaje("Mario", "Plomero", "6", R.drawable.mario_mario), id);
        this.agregarPersonaje(db, new Personaje("Luigi", "Plomero", "5", R.drawable.mario_luigi_chico), id);
        this.agregarPersonaje(db, new Personaje("Peach", "Princesa", "1", R.drawable.mario_peach), id);
        this.agregarPersonaje(db, new Personaje("Toad", "Hongo", "1", R.drawable.mario_toad_chico), id);

        this.agregarObjeto(db, new Objeto("Hongo Rojo", "2"), id);
        this.agregarObjeto(db, new Objeto("Hongo Verde", "1"), id);
        this.agregarObjeto(db, new Objeto("Estrella", "6"), id);
        this.agregarObjeto(db, new Objeto("Flor", "9"), id);

        Mision bomb_battlefield = new Mision("Bob-omb Battlefield", "Bob-omb Battlefield is the first course in Super Mario 64 and Super Mario 64 DS. The painting through which Mario can enter Bob-omb Battlefield is located on the first floor of Princess Peach's Castle, through the door on the far left. It does not require any Power Stars to enter. Bob-omb Battlefield takes place in a grassland area with many trees. It contains several mountains and hills which can be climbed. Bob-omb Battlefield is always in constant war, with the Black Bob-ombs fighting against the Bob-omb Buddies. Mario's enemies include Bob-ombs, Goombas, a Koopa Troopa and a Chain Chomp, the only one in Super Mario 64. There are several Wing Cap ! Blocks and six cannons in the level.", "6 Estrellas");
        Mision whomp_fortress = new Mision("Whomp's Fortress", "Whomp's Fortress, also known as the Whomp King's Fortress[1], is the stronghold home of the Whomp race and their leader, the Whomp King. It is the second level in Super Mario 64 and its Nintendo DS remake, Super Mario 64 DS, and can be accessed via a painting on the first floor of Princess Peach's Castle. Only one Power Star is needed to open the door to the painting. In Super Mario 64 DS, a few minor changes were made, and in Super Mario Galaxy 2, the Throwback Galaxy is essentially a replica of Whomp's Fortress.", "5 Estrellas");
        Mision Jolly_Roger_Bay = new Mision("Jolly Roger Bay", "Jolly Roger Bay is the third course in Super Mario 64 and its remake, Super Mario 64 DS. It is a stage filled with water and surrounded by cliffs. Above the water are two Metal Cap blocks, a cannon and a ! Switch to make a bridge to a sunken pirate ship (once it rises out of the water). Under the water is the ship in the first level, Unagi when that level is completed, a third Metal Cap, and the tunnel to the pirates cave. The entrance to Jolly Roger Bay is on the main floor of Peach's castle, up the stairs to the right, and behind the star door labeled \"3\", meaning that three Power Stars are required to enter. Along with Dire, Dire Docks, one of this stage's most unique features is that another layer of instruments is added to the song as Mario dives in the water, and another as Mario enters the cave. Jolly Roger is also the name given to the typical pirate flags. It is also the name of the infamous pirate ship of Captain Hook, the pirate from Peter Pan.", "2 Estrellas");
        Mision Cool_Cool_Mountain = new Mision("Cool, Cool Mountain", "Cool, Cool Mountain is the fourth level found inside a painting in the games Super Mario 64 and Super Mario 64 DS. The entrance is on the first floor of Princess Peach's Castle, and behind the other star door labeled \"3\". It is the home of several Penguins and Spindrifts. There are three cannons, a Slide, and a teleportation area at the end of the broken bridges. Bottomless pits surround this mountain, as it appears to be floating in the sky, so it is possible to fall off the course. Mario begins at the summit (unlike Tall, Tall Mountain) and must progress down the icy slopes all around the mountain. At the summit is a house with an oversized chimney that Mario can enter. Inside is a large penguin that will race Mario on a huge ice slide. In between the top and bottom is the Headless Snowman. A quick reference to Santa Claus, who has a more major role in other forms of Mario media (such as cartoons), is made on a sign near the starting point of Cool, Cool Mountain.", "6 Estrellas");
        Mision Rainbow_Ride = new Mision("Rainbow Ride", "Rainbow Ride is the fifteenth and last course in Super Mario 64 and Super Mario 64 DS. In Japan and Korea, the course is known as Rainbow Cruise. It is accessible in the highest room in Princess Peach's Castle, which also contains the gate to Tick Tock Clock. Unlike most courses in the game, Rainbow Ride is not entered through a painting, but through a hole in a niche opposite to that of Wing Mario Over the Rainbow.", "7 Estrellas");

        this.agregarMision(db, bomb_battlefield, id);
        this.agregarMision(db, whomp_fortress, id);
        this.agregarMision(db, Jolly_Roger_Bay, id);
        this.agregarMision(db, Cool_Cool_Mountain, id);
        this.agregarMision(db, Rainbow_Ride, id);
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
            juego.setFotoId(R.drawable.ic_juego);

//            Bitmap bMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_juego);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            if (bMap.compress(Bitmap.CompressFormat.PNG, 50, stream))
//                Log.e("BITMAPPPP", "NO SE PUDO SACAR LOS BITS!!!!");
//            byte[] bitmapdata = stream.toByteArray();
//            juego.setLaFoto(bitmapdata);

            juego.setLaFoto(new byte[]{0, 1, 0});

            l.add(this.agregarJuego(db, juego));
        }
        return l;
    }

    private void agregarPersonajes(SQLiteDatabase db, int size, List<Long> juegos_ids) {
        for (long id : juegos_ids) {
            for (int i = 3; i <= size; i++) {
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
                this.agregarMision(db, new Mision("MISION_" + id + "-" + i, "Descripcion que suele ser un poco mas larga_" + id + "-" + i, "80%"), id);
            }
        }
    }


}
