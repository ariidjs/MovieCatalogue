package com.rizkhan.moviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.rizkhan.moviecatalogue.db.MovieContract.TABLE_NAME;

public class MovieHelper {
    public static final String TAG = "FAVORITE";
    private static String DATABASE_TABLE = TABLE_NAME;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqliteDatabase;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        Log.i(TAG, "Database Opened");
        databaseHelper = new DatabaseHelper(context);
        sqliteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        Log.i(TAG, "Database Closed");
        sqliteDatabase.close();
    }


    public Cursor queryProvider() {
        return sqliteDatabase.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                MovieContract.MovieEntry._ID + " DESC"
        );
    }

    public Cursor queryByIdProvider(String id) {
        return sqliteDatabase.query(DATABASE_TABLE, null
                , MovieContract.MovieEntry._ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long addFavorite(ContentValues values) {
        return sqliteDatabase.insert(DATABASE_TABLE, null, values);
    }

    public int updateFavorite(String id, ContentValues values) {
        return sqliteDatabase.update(DATABASE_TABLE, values,
                MovieContract.MovieEntry._ID + " = ?", new String[]{id});
    }

    public int deleteFavorite(String id) {
        return sqliteDatabase.delete(DATABASE_TABLE,
                MovieContract.MovieEntry._ID + " = ?", new String[]{id});
    }
}
