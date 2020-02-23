package com.rizkhan.favoriteapp.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class MovieContract {
    public static final String TABLE_NAME = "movies_table";

    public static final class MovieEntry implements BaseColumns {
        public static final String _ID = "movie_id";
        public static final String TITLE = "title";
        public static final String USER_RATING = "rating";
        public static final String POSTER_PATH = "image_path";
        public static final String RELEASE_DATE = "date";
        public static final String POPULAR = "popular";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String OVERVIEW = "overview";
    }

    public static final String AUTHORITY = "com.rizkhan.moviecatalogue.movie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
