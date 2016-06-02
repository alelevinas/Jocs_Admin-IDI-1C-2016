package com.example.alejandro.jocs_admin_posta.db_utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



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

        DatabaseManager.getInstance().populateDb(db);
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

}