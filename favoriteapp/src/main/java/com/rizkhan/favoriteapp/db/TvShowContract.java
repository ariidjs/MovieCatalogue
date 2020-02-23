package com.rizkhan.favoriteapp.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class TvShowContract {
    public static final String TABLE_TV = "tv_show0709";

    public static final class TvShowEntry implements BaseColumns {
        public static final String _ID = "tv_id";
        public static final String TITLE = "tv_title";
        public static final String USER_RATING = "tv_rating";
        public static final String POSTER_PATH = "tv_image_path";
        public static final String RELEASE_DATE = "tv_date";
        public static final String POPULAR = "tv_popular";
        public static final String OVERVIEW = "tv_overview";
    }
    
    public static final String AUTHORITY = "com.rizkhan.moviecatalogue.tvshow";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_TV)
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
