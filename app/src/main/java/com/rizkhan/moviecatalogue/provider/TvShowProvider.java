package com.rizkhan.moviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rizkhan.moviecatalogue.db.TvShowHelper;

import java.util.Objects;

import static com.rizkhan.moviecatalogue.db.TvShowContract.AUTHORITY;
import static com.rizkhan.moviecatalogue.db.TvShowContract.CONTENT_URI;
import static com.rizkhan.moviecatalogue.db.TvShowContract.TABLE_TV;

public class TvShowProvider extends ContentProvider {
    private static final int TV = 1;
    private static final int TV_ID = 2;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_TV, TV);
        URI_MATCHER.addURI(AUTHORITY, TABLE_TV + "/#", TV_ID);
    }

    private TvShowHelper tvShowHelper;

    @Override
    public boolean onCreate() {
        tvShowHelper = new TvShowHelper(getContext());
        tvShowHelper.open();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;

        switch (URI_MATCHER.match(uri)) {
            case TV:
                cursor = tvShowHelper.queryProvider();
                break;

            case TV_ID:
                cursor = tvShowHelper.queryByIdProvider(uri.getLastPathSegment());
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
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;

        switch (URI_MATCHER.match(uri)) {
            case TV:
                added = tvShowHelper.addFavorite(contentValues);
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
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int tvDeleted;
        switch (URI_MATCHER.match(uri)) {
            case TV_ID:
                tvDeleted = tvShowHelper.deleteFavorite(uri.getLastPathSegment());
                break;
            default:
                tvDeleted = 0;
                break;
        }

        if (tvDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return tvDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int tvUpdated;
        switch (URI_MATCHER.match(uri)) {
            case TV_ID:
                tvUpdated = tvShowHelper.updateFavorite(uri.getLastPathSegment(), contentValues);
                break;

            default:
                tvUpdated = 0;
                break;
        }

        if (tvUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return tvUpdated;
    }
}
