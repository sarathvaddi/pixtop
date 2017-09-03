package com.example.vaddisa.pixtop.PictureDB;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.vaddisa.pixtop.PictureDetails;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class ImageCache {

    private ImageCache() {
    }

    public static PictureDetails toModel(Cursor cursor) {

        PictureDetails pictureDetails = new PictureDetails();;
        pictureDetails.setId_hash(cursor.getString(cursor.getColumnIndex(PicturesContract.PicturesEntry.ID_HASH)));
        pictureDetails.setTitle(cursor.getString(cursor.getColumnIndex(PicturesContract.PicturesEntry.COLUMN_TITLE)));
        pictureDetails.setPoster_path(cursor.getString(cursor.getColumnIndex(PicturesContract.PicturesEntry.COLUMN_POSTER_IMAGE)));
        pictureDetails.setOverview(cursor.getString(cursor.getColumnIndex(PicturesContract.PicturesEntry.COLUMN_SYNOPSIS)));
        pictureDetails.setRelease_date(cursor.getString(cursor.getColumnIndex(PicturesContract.PicturesEntry.COLUMN_RELEASE_DATE)));
        pictureDetails.setOriginal_title(cursor.getString(cursor.getColumnIndex(PicturesContract.PicturesEntry.COLUMN_ORIGINAL_TITLE)));

        return pictureDetails;
    }

    public static ContentValues toContentValues(PictureDetails picture) {
        ContentValues values = new ContentValues();
        values.put(PicturesContract.PicturesEntry.ID_HASH, picture.getId_hash());
        values.put(PicturesContract.PicturesEntry.COLUMN_POSTER_IMAGE, picture.getPoster_path());
        values.put(PicturesContract.PicturesEntry.COLUMN_RELEASE_DATE, picture.getRelease_date());
        values.put(PicturesContract.PicturesEntry.COLUMN_SYNOPSIS, picture.getOverview());
        values.put(PicturesContract.PicturesEntry.COLUMN_TITLE, picture.getTitle());
        values.put(PicturesContract.PicturesEntry.COLUMN_ORIGINAL_TITLE, picture.getOriginal_title());
        return values;
    }
}



