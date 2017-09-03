package com.example.vaddisa.pixtop.PictureDB;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by vaddisa on 8/29/2017.
 */
public class PicturesContract {

    public static final String CONTENT_AUTHORITY = "com.example.vaddisa.pixtop";

    /**
     * The base of all URI's which apps will use to contact the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Path to look for Pictures data.
     */
    public static final String PICTURES_PATH = "pictures";

    /**
     * Defines the contents of the Picture table.
     */
    public static final class PicturesEntry implements PicturesColumn {
        /**
         * Table name to save the Picture data.
         */
        public static final String TABLE_NAME = "pictures";

        /**
         * Column name to store the original title for the movie.
         */
        public static final String COLUMN_TITLE = "title";

        /**
         * Column name to store the original title for the movie.
         */
        public static final String COLUMN_SYNOPSIS = "synopsis";

        /**
         * Column name to store the original title for the movie.
         */
        public static final String COLUMN_RELEASE_DATE = "release_date";

        /**
         * Column name to store the original title for the movie.
         */
        public static final String COLUMN_POSTER_IMAGE = "poster_image";


        public static final String ID_HASH = "id_hash";

        public static final String COLUMN_ORIGINAL_TITLE = "original_title";

        /**
         * Uri to look for movie data.
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PICTURES_PATH).build();

        /**
         * MIME type to represent a directory with movie items.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PICTURES_PATH;

        /**
         * MIME type to represent a movie item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PICTURES_PATH;



        /**
         * Creates a new uri with the id appended to the end of the path.
         *
         * @param id Id of the movie.
         * @return Uri with the id appended to the end of the path.
         */
        public static Uri buildMovieUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }


}
