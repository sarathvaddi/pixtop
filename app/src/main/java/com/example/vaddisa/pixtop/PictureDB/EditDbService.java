package com.example.vaddisa.pixtop.PictureDB;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.vaddisa.pixtop.PictureDetails;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by vaddisa on 8/20/2017.
 */
public class EditDbService extends IntentService {


    private static final String TAG = EditDbService.class.getSimpleName();
    public static final String PICTURES_KEY = "picture_key";

    PictureDetails imageDetailsModel;
    private FirebaseAnalytics mFirebaseAnalytics;

    public EditDbService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        imageDetailsModel = intent.getParcelableExtra(PICTURES_KEY);

        String[] projection = new String[]{PicturesContract.PicturesEntry.ID_HASH};
        String selection = PicturesContract.PicturesEntry.ID_HASH + "=?";
        String[] selectionArgs = new String[1];
        selectionArgs[0] = "" + imageDetailsModel.getId_hash();

        Cursor pictureCursor = getApplicationContext().getContentResolver().query(PicturesContract.PicturesEntry.CONTENT_URI,
                projection, selection, selectionArgs, null);
        if (pictureCursor != null) {
            if (!pictureCursor.moveToFirst()) {

                getApplicationContext().getContentResolver().insert(PicturesContract.PicturesEntry.CONTENT_URI,
                        ImageCache.toContentValues(imageDetailsModel));

                notifyFavUpdate(imageDetailsModel.getTitle() + "Added to db");
            } else {
                deleteFavourite();
            }

            pictureCursor.close();

        }

    }

    private void deleteFavourite() {

        String selectionPicture = PicturesContract.PicturesEntry.ID_HASH + "=?";
        String[] selectionArgs = new String[1];
        selectionArgs[0] = "" + imageDetailsModel.getId_hash();

        getApplicationContext().getContentResolver().delete(PicturesContract.PicturesEntry.CONTENT_URI,
                selectionPicture, selectionArgs);
        notifyFavUpdate(imageDetailsModel.getTitle() + "Removed from db");

    }

    private void notifyFavUpdate(String text) {
        final String message = String.format(text, imageDetailsModel.getTitle());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, message);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
