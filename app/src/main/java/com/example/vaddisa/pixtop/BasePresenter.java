package com.example.vaddisa.pixtop;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.vaddisa.pixtop.ConnectivityManager.ConnectionManager;
import com.example.vaddisa.pixtop.PictureDB.ImageCache;
import com.example.vaddisa.pixtop.PictureDB.PicturesContract;

import java.util.ArrayList;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class BasePresenter implements LoaderManager.LoaderCallbacks<Cursor> {
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

    public void fetchFavouritePictures(LoaderManager loaderManager) {
        loaderManager.initLoader(100, null, this).forceLoad();
    }


    public boolean isFavAvailableInDb(String movieID) {
        if (movieID != null) {
            String[] projection = new String[]{PicturesContract.PicturesEntry.ID_HASH};
            String selection = PicturesContract.PicturesEntry.ID_HASH + "=?";
            String[] selectionArgs = new String[1];
            selectionArgs[0] = movieID;

            Cursor movieCursor = ctx.getContentResolver().query(PicturesContract.PicturesEntry.CONTENT_URI,
                    projection, selection, selectionArgs, null);
            if (movieCursor != null)
                return movieCursor.moveToFirst();
        }

        return false;
    }

    public void setResult(ArrayList<PictureDetails> result) {
        this.list = result;
    }

    public ArrayList<PictureDetails> getList() {
        return list;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(ctx,
                PicturesContract.PicturesEntry.CONTENT_URI,
                PicturesContract.PicturesEntry.PICTURE_COLUMNS,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<PictureDetails> movies = new ArrayList<>();
        if (data != null) {
            while (data.moveToNext()) {
                PictureDetails movie = ImageCache.toModel(data);
                movies.add(movie);
            }
        }
        resultInterface.getResult(movies);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

