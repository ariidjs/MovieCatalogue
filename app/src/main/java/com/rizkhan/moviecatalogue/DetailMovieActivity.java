package com.rizkhan.moviecatalogue;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.snackbar.Snackbar;
import com.rizkhan.moviecatalogue.buildconfig.Config;
import com.rizkhan.moviecatalogue.db.MovieContract;
import com.rizkhan.moviecatalogue.db.MovieHelper;
import com.rizkhan.moviecatalogue.model.Movies;
import com.rizkhan.moviecatalogue.widget.MovieTvWidget;

import static com.rizkhan.moviecatalogue.db.MovieContract.CONTENT_URI;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    public TextView title, releaseDate, popularity, rating, desc;
    public ImageView imageDetail;
    public String titleFilm, mReleaseDate, mPopularity, mRating, mDesc, mImage, imageFav, rate, mId, backdropPath;
    MaterialFavoriteButton materialFavoriteButton;
    private MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.detail));
        }

        title = findViewById(R.id.txt_title_detail);
        releaseDate = findViewById(R.id.txt_fill_release);
        popularity = findViewById(R.id.txt_fill_popular);
        rating = findViewById(R.id.txt_fill_rating);
        desc = findViewById(R.id.txt_desc);
        imageDetail = findViewById(R.id.image_detail);


        Movies listMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        titleFilm = listMovie.getTitle();
        mReleaseDate = listMovie.getReleaseDate();
        mPopularity = String.valueOf(listMovie.getPopularity());
        mRating = String.valueOf(listMovie.getVoteAverage());
        mDesc = listMovie.getOverview();
        mImage = Config.IMAGE_URL_BASE_PATH + listMovie.getPosterPath();
        imageFav = listMovie.getPosterPath();
        mId = String.valueOf(listMovie.getId());
        rate = listMovie.getVoteAverage();
        backdropPath = listMovie.getBackdropPath();

        Glide.with(this).load(mImage).apply(new RequestOptions().override(350, 550))
                .into(imageDetail);
        title.setText(titleFilm);
        releaseDate.setText(mReleaseDate);
        popularity.setText(mPopularity);
        rating.setText(mRating);
        desc.setText(mDesc);

        movieHelper = new MovieHelper(this);
        movieHelper.open();
        //button fav
        materialFavoriteButton = findViewById(R.id.favorite_button);
        if (isExists(mId)) {
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    (MaterialFavoriteButton buttonView, boolean favorite) -> {
                            if (favorite) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                movieHelper.deleteFavorite(mId);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                    });
        } else {
            materialFavoriteButton.setFavorite(false);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    (MaterialFavoriteButton buttonView, boolean favorite) -> {
                            if (favorite) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                movieHelper.deleteFavorite(mId);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                    });
        }
        AppWidgetManager awm = AppWidgetManager.getInstance(this);
        ComponentName cn = new ComponentName(this, MovieTvWidget.class);
        awm.notifyAppWidgetViewDataChanged(awm.getAppWidgetIds(cn),R.id.stack_view);
    }

    public boolean isExists(String searchItem) {
        Cursor cursor = movieHelper.queryByIdProvider(String.valueOf(searchItem));
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void saveFavorite() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry._ID, mId);
        contentValues.put(MovieContract.MovieEntry.TITLE, titleFilm);
        contentValues.put(MovieContract.MovieEntry.USER_RATING, mRating);
        contentValues.put(MovieContract.MovieEntry.POSTER_PATH, imageFav);
        contentValues.put(MovieContract.MovieEntry.RELEASE_DATE, mReleaseDate);
        contentValues.put(MovieContract.MovieEntry.POPULAR, mPopularity);
        contentValues.put(MovieContract.MovieEntry.BACKDROP_PATH, backdropPath);
        contentValues.put(MovieContract.MovieEntry.OVERVIEW, mDesc);
        getApplicationContext().getContentResolver().insert(CONTENT_URI, contentValues);
    }
}
