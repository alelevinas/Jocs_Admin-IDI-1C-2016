package com.example.alejandro.jocs_admin_posta.db_utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alejandro.jocs_admin_posta.model.Juego;

/**
 * Created by Alejandro on 31/5/2016.
 */
public class DatabaseManager {
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

    public long agregarJuego(Juego juego, long[] tag_ids) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

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


}
