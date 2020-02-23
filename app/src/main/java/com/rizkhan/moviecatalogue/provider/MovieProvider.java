package com.rizkhan.moviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rizkhan.moviecatalogue.db.MovieHelper;

import java.util.Objects;

import static com.rizkhan.moviecatalogue.db.MovieContract.AUTHORITY;
import static com.rizkhan.moviecatalogue.db.MovieContract.CONTENT_URI;
import static com.rizkhan.moviecatalogue.db.MovieContract.TABLE_NAME;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME, MOVIE);
        URI_MATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIE_ID);
    }

    private MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;

        switch (URI_MATCHER.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;

            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;

        switch (URI_MATCHER.match(uri)) {
            case MOVIE:
                added = movieHelper.addFavorite(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int movieDeleted;
        switch (URI_MATCHER.match(uri)) {
            case MOVIE_ID:
                movieDeleted = movieHelper.deleteFavorite(uri.getLastPathSegment());
                break;
            default:
                movieDeleted = 0;
                break;
        }

        if (movieDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return movieDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int movieUpdated;
        switch (URI_MATCHER.match(uri)) {
            case MOVIE_ID:
                movieUpdated = movieHelper.updateFavorite(uri.getLastPathSegment(), values);
                break;

            default:
                movieUpdated = 0;
                break;
        }

        if (movieUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return movieUpdated;
    }

}
