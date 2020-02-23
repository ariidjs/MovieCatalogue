package com.rizkhan.moviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static com.rizkhan.moviecatalogue.db.TvShowContract.TABLE_TV;

public class TvShowHelper {

    public static final String TAG = "FAVORITE_TV";
    private static String DATABASE_TABLE = TABLE_TV;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqliteDatabase;

    public TvShowHelper(Context context) {
        this.context = context;
    }

    public TvShowHelper open() throws SQLException {
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
                TvShowContract.TvShowEntry._ID + " DESC"
        );
    }

    public Cursor queryByIdProvider(String id) {
        return sqliteDatabase.query(DATABASE_TABLE, null
                , TvShowContract.TvShowEntry._ID + " = ?"
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
                TvShowContract.TvShowEntry._ID + " = ?", new String[]{id});
    }

    public int deleteFavorite(String id) {
        return sqliteDatabase.delete(DATABASE_TABLE,
                TvShowContract.TvShowEntry._ID + " = ?", new String[]{id});
    }
}
