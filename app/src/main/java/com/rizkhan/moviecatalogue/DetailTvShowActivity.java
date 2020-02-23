package com.rizkhan.moviecatalogue;

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
import com.rizkhan.moviecatalogue.db.TvShowContract;
import com.rizkhan.moviecatalogue.db.TvShowHelper;
import com.rizkhan.moviecatalogue.model.TvShow;

import static com.rizkhan.moviecatalogue.db.TvShowContract.CONTENT_URI;


public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_DETAIL = "extra_detail";
    public TextView txtTvTitle, txtTvDate, txtTvPopular, txtTvRating, txtTvDesc;
    public ImageView imgTvDetail;
    public String titleTv, tvReleaseDate, tvPopularity, tvRating, tvDesc, tvImage, tvImageFav, tvId;
    public MaterialFavoriteButton materialFavoriteButton;
    private TvShowHelper tvShowHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.detail_tv));
        }

        txtTvTitle = findViewById(R.id.txt_title_detail_tv);
        txtTvDesc = findViewById(R.id.txt_tv_desc);
        txtTvDate = findViewById(R.id.txt_tv_date);
        txtTvPopular = findViewById(R.id.txt_tv_fill_popular);
        txtTvRating = findViewById(R.id.txt_tv_fill_rating);
        imgTvDetail = findViewById(R.id.image_detail_tv);


        TvShow listTv = getIntent().getParcelableExtra(EXTRA_DETAIL);
        tvId = String.valueOf(listTv.getId());
        titleTv = listTv.getOriginalName();
        tvReleaseDate = listTv.getFirstAirDate();
        tvPopularity = String.valueOf(listTv.getPopularity());
        tvRating = String.valueOf(listTv.getVoteAverage());
        tvDesc = listTv.getOverview();
        tvImageFav = listTv.getPosterPath();
        tvImage = Config.IMAGE_URL_BASE_PATH + listTv.getPosterPath();


        Glide.with(this).load(tvImage).apply(new RequestOptions().override(350, 550))
                .into(imgTvDetail);
        txtTvTitle.setText(titleTv);
        txtTvDate.setText(tvReleaseDate);
        txtTvPopular.setText(tvPopularity);
        txtTvRating.setText(tvRating);
        txtTvDesc.setText(tvDesc);


        tvShowHelper = new TvShowHelper(this);
        tvShowHelper.open();
        //button fav
        materialFavoriteButton = findViewById(R.id.tv_favorite_button);

        if (isExists(tvId)) {
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    (MaterialFavoriteButton buttonView, boolean favorite) -> {
                            if (favorite) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                tvShowHelper.deleteFavorite(tvId);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                    });
        } else {
            materialFavoriteButton.setFavorite(false);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    (MaterialFavoriteButton buttonView, boolean favorite) ->{
                            if (favorite) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                tvShowHelper.deleteFavorite(String.valueOf(tvId));
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                    });
        }
    }

    public boolean isExists(String searchItem) {
        Cursor cursor = tvShowHelper.queryByIdProvider(searchItem);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void saveFavorite() {
        ContentValues values = new ContentValues();
        values.put(TvShowContract.TvShowEntry._ID, tvId);
        values.put(TvShowContract.TvShowEntry.TITLE, titleTv);
        values.put(TvShowContract.TvShowEntry.USER_RATING, tvRating);
        values.put(TvShowContract.TvShowEntry.POSTER_PATH, tvImageFav);
        values.put(TvShowContract.TvShowEntry.RELEASE_DATE, tvReleaseDate);
        values.put(TvShowContract.TvShowEntry.POPULAR, tvPopularity);
        values.put(TvShowContract.TvShowEntry.OVERVIEW, tvDesc);
        getContentResolver().insert(CONTENT_URI, values);
    }
}
