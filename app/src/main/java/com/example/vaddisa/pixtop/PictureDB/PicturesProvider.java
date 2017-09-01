package com.example.vaddisa.pixtop.PictureDB;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by vaddisa on 8/29/2017.
 */
public class PicturesProvider extends ContentProvider {

    /**
     * Id to request the full list of movies.
     */
    public static final int PICTURE = 100;

    /**
     * Id to request the Movies by Id.
     */
    public static final int PICTURE_BY_ID = 101;


    /**
     * Manages the data base.
     */
    private PicturesdbHelper dbHelper;

    /**
     * Uri matcher used for this provider.
     */
    private static final UriMatcher uriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        dbHelper = new PicturesdbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor result;

        final int match = buildUriMatcher().match(uri);
        switch (match) {
            case PICTURE:
            case PICTURE_BY_ID:
                result = dbHelper.getReadableDatabase().query(PicturesContract.PicturesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return result;
    }

    @Override
    public String getType(Uri uri) {
        final int match = buildUriMatcher().match(uri);
        switch (match) {
            case PICTURE:
            case PICTURE_BY_ID:
                return PicturesContract.PicturesEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        Uri result;

        switch (match) {
            case PICTURE: {
                long id = database.insert(PicturesContract.PicturesEntry.TABLE_NAME, null, values);
                if (id > 0)
                    result = PicturesContract.PicturesEntry.buildMovieUri(Long.toString(id));
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);

                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        database.close();
        getContext().getContentResolver().notifyChange(result, null);

        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        int rowsDeleted = 0;

        if (null == selection) selection = "1";

        switch (match) {
            case PICTURE: {
                rowsDeleted = database.delete(PicturesContract.PicturesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        database.close();

        if (rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        int rowsUpdated = 0;

        switch (match) {
            case PICTURE: {
                rowsUpdated = database.update(PicturesContract.PicturesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        database.close();

        if (rowsUpdated != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        switch (match) {
            case PICTURE: {
                return insertBulkData(PicturesContract.PicturesEntry.TABLE_NAME, uri, values, database);
            }
            default:
                return super.bulkInsert(uri, values);
        }
    }

    private int insertBulkData(String table, Uri uri, ContentValues[] values, SQLiteDatabase database) {
        int rowsInserted = 0;
        database.beginTransaction();
        try {
            for (ContentValues value : values) {
                long id = database.insert(table, null, value);
                if (id != -1)
                    rowsInserted++;
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsInserted;
    }

    @Override
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }

    /***
     * This UriMatcher will match each URI to the PICTURE and SORTED_MOVIE integer constants defined
     * above. UriMatcher to select the query to execute.
     *
     * @return UriMatcher to select the query to execute.
     */
    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(PicturesContract.CONTENT_AUTHORITY, PicturesContract.PICTURES_PATH, PICTURE);
        matcher.addURI(PicturesContract.CONTENT_AUTHORITY, PicturesContract.PICTURES_PATH + "/*", PICTURE_BY_ID);
        return matcher;
    }
}