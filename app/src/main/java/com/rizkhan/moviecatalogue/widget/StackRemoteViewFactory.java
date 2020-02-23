package com.rizkhan.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.rizkhan.moviecatalogue.R;
import com.rizkhan.moviecatalogue.buildconfig.Config;
import com.rizkhan.moviecatalogue.model.Movies;

import static com.rizkhan.moviecatalogue.db.MovieContract.CONTENT_URI;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Cursor cursor;

    StackRemoteViewFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        final long identify = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(CONTENT_URI, null, null,
                null, null);

        Binder.restoreCallingIdentity(identify);
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identify = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(CONTENT_URI, null, null,
                null, null);

        Binder.restoreCallingIdentity(identify);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Movies movies = getItem(i);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);
        if (movies != null) {


            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .asBitmap()
                        .load(Config.IMAGE_URL_BASE_PATH + movies.getBackdropPath())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();

            } catch (Exception e) {
                e.printStackTrace();
            }

            remoteViews.setImageViewBitmap(R.id.image_widget, bitmap);
            remoteViews.setTextViewText(R.id.txt_title_widget, movies.getTitle());

            Bundle extras = new Bundle();
            extras.putInt(MovieTvWidget.EXTRA_ITEM, i);
            Intent fillIntent = new Intent();
            fillIntent.putExtras(extras);

            remoteViews.setOnClickFillInIntent(R.id.image_widget, fillIntent);
        }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    private Movies getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Error");
        }
        return new Movies(cursor);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
