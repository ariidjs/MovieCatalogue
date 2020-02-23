package com.rizkhan.moviecatalogue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.rizkhan.moviecatalogue.db.MovieContract.TABLE_NAME;
import static com.rizkhan.moviecatalogue.db.TvShowContract.TABLE_TV;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_movie";
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_NAME,
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.TITLE,
            MovieContract.MovieEntry.USER_RATING,
            MovieContract.MovieEntry.POSTER_PATH,
            MovieContract.MovieEntry.RELEASE_DATE,
            MovieContract.MovieEntry.POPULAR,
            MovieContract.MovieEntry.BACKDROP_PATH,
            MovieContract.MovieEntry.OVERVIEW
    );
    private static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_TV,
            TvShowContract.TvShowEntry._ID,
            TvShowContract.TvShowEntry.TITLE,
            TvShowContract.TvShowEntry.USER_RATING,
            TvShowContract.TvShowEntry.POSTER_PATH,
            TvShowContract.TvShowEntry.RELEASE_DATE,
            TvShowContract.TvShowEntry.POPULAR,
            TvShowContract.TvShowEntry.OVERVIEW
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TV);
        onCreate(db);
    }
}
