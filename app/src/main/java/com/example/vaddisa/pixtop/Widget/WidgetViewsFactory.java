package com.example.vaddisa.pixtop.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.vaddisa.pixtop.PictureDB.ImageCache;
import com.example.vaddisa.pixtop.PictureDB.PicturesContract;
import com.example.vaddisa.pixtop.PictureDetails;
import com.example.vaddisa.pixtop.R;
import com.example.vaddisa.pixtop.Views.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sarathreddyvaddhi on 9/1/17.
 */

public class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private Context mContext;
    private int appWidgetId;
    private Cursor cursor;

    final String[] PICTURES_COLUMN = {
            PicturesContract.PicturesEntry.ID_HASH,
            PicturesContract.PicturesEntry.COLUMN_POSTER_IMAGE,
            PicturesContract.PicturesEntry.COLUMN_RELEASE_DATE,
            PicturesContract.PicturesEntry.COLUMN_SYNOPSIS,
            PicturesContract.PicturesEntry.COLUMN_TITLE,
            PicturesContract.PicturesEntry.COLUMN_ORIGINAL_TITLE
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    public WidgetViewsFactory(Context context, Intent intent) {
        mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {
        Log.d("TAG", "Widget :: onCreate");
        cursor = mContext.getContentResolver().query(PicturesContract.PicturesEntry.CONTENT_URI,
                PICTURES_COLUMN, null, null, null);
    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        if (cursor != null) {
            cursor.close();
        }
        Log.d("TAG", "Widget :: onDataSetChanged");
        cursor = mContext.getContentResolver().query(PicturesContract.PicturesEntry.CONTENT_URI,
                PICTURES_COLUMN, null, null, null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Log.d("TAG", "Widget :: getViewAt : " + position);

        RemoteViews row = new RemoteViews(mContext.getPackageName(),
                R.layout.list_widget_layout);

        if (cursor != null) {
            cursor.moveToPosition(position);
            PictureDetails picDetails = ImageCache.toModel(cursor);

            Bitmap bitmap = null;
            InputStream iStream = null;
            try {
                URL url = new URL(picDetails.getOverview());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                iStream = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(iStream);

            } catch (Exception e) {
                Log.d("Exceptiondownloadingurl", e.toString());
            } finally {
                try {
                    if (iStream != null)
                        iStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            row.setImageViewBitmap(R.id.image, bitmap);
        }


        Intent clickIntent = new Intent(mContext, MainActivity.class);
        row.setOnClickFillInIntent(R.id.image, clickIntent);

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
