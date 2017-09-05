package com.example.vaddisa.pixtop.PictureDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vaddisa on 8/29/2017.
 */
public class PicturesdbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;

    public static final String DATABASE_NAME = "favourite_pictures.db";


    public PicturesdbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + PicturesContract.PicturesEntry.TABLE_NAME + " (" +
                PicturesContract.PicturesEntry.ID_HASH + " TEXT PRIMARY KEY NOT NULL," +
                PicturesContract.PicturesEntry.COLUMN_TITLE + " TEXT  NULL," +
                PicturesContract.PicturesEntry.COLUMN_SYNOPSIS + " TEXT NULL," +
                PicturesContract.PicturesEntry.COLUMN_RELEASE_DATE + " TEXT  NULL," +
                PicturesContract.PicturesEntry.COLUMN_ORIGINAL_TITLE + " TEXT NULL," +
                PicturesContract.PicturesEntry.COLUMN_POSTER_IMAGE + " TEXT NULL);";


        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PicturesContract.PicturesEntry.TABLE_NAME);
        onCreate(db);
    }
}
