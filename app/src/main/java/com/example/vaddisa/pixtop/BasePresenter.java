package com.example.vaddisa.pixtop;

import android.content.Context;
import android.database.Cursor;

import com.example.vaddisa.pixtop.ConnectivityManager.ConnectionManager;
import com.example.vaddisa.pixtop.PictureDB.ImageCache;
import com.example.vaddisa.pixtop.PictureDB.PicturesContract;

import java.util.ArrayList;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class BasePresenter {
    Object o;
    Context ctx;
    Result resultInterface;
    ArrayList<PictureDetails> list;

    public BasePresenter(Context ctx, Result resultInterface) {
        this.ctx = ctx;
        this.resultInterface = resultInterface;
    }


    public BasePresenter(Context ctx) {
        this.ctx = ctx;
    }


    public void makeNetworkCall(String url, boolean isLocalApi, Context ctx) {
        ConnectionManager cm = new ConnectionManager(this, ctx, isLocalApi);
        cm.execute(url);
    }

    public void fetchFavouritePictures() {

        final String[] PICTURES_COLUMN = {
                PicturesContract.PicturesEntry.ID_HASH,
                PicturesContract.PicturesEntry.COLUMN_POSTER_IMAGE,
                PicturesContract.PicturesEntry.COLUMN_RELEASE_DATE,
                PicturesContract.PicturesEntry.COLUMN_SYNOPSIS,
                PicturesContract.PicturesEntry.COLUMN_TITLE,
        };

        Cursor movieCursor = ctx.getContentResolver().query(PicturesContract.PicturesEntry.CONTENT_URI, PICTURES_COLUMN, null, null, null);

        ArrayList<PictureDetails> movies = new ArrayList<>();
        if(movieCursor!=null) {
            while (movieCursor.moveToNext()) {
                PictureDetails movie = ImageCache.toModel(movieCursor);
                movies.add(movie);
            }
            movieCursor.close();
        }

//        setResult(movies);
        resultInterface.getResult(movies);
    }


    public boolean isFavAvailableInDb(String movieID) {
        String[] projection = new String[]{PicturesContract.PicturesEntry.ID_HASH};
        String selection = PicturesContract.PicturesEntry.ID_HASH + "=?";
        String[] selectionArgs = new String[1];
        selectionArgs[0] = movieID;

        Cursor movieCursor = ctx.getContentResolver().query(PicturesContract.PicturesEntry.CONTENT_URI,
                projection, selection, selectionArgs, null);
        if (movieCursor != null)
            return movieCursor.moveToFirst();


        return false;
    }

    public void setResult(ArrayList<PictureDetails> result) {
        this.list = result;
    }

    public ArrayList<PictureDetails> getList() {
        return list;
    }
}

