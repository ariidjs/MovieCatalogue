package com.rizkhan.favoriteapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rizkhan.favoriteapp.R;
import com.rizkhan.favoriteapp.buildconfig.Config;

import static com.rizkhan.favoriteapp.db.MovieContract.MovieEntry.POSTER_PATH;
import static com.rizkhan.favoriteapp.db.MovieContract.MovieEntry.TITLE;
import static com.rizkhan.favoriteapp.db.MovieContract.MovieEntry.USER_RATING;
import static com.rizkhan.favoriteapp.db.MovieContract.getColumnString;

public class MovieFavoriteAdapter extends CursorRecyclerAdapter<MovieFavoriteAdapter.SimpleViewHolder> {
    private Context mContext;
    public MovieFavoriteAdapter(Context context) {
        super(null);
        mContext = context;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View formNameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_movie, parent, false);
        return new SimpleViewHolder(formNameView);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, Cursor cursor) {
        String image_url = Config.IMAGE_URL_BASE_PATH + getColumnString(cursor, POSTER_PATH);
        String title = getColumnString(cursor, TITLE);
        String rating = getColumnString(cursor, USER_RATING);

        Glide.with(mContext).load(image_url).apply(new RequestOptions().override(350, 550))
                .into(holder.imgPoster);
        holder.txtTitle.setText(title);
        holder.txtRating.setText(rating);
    }

    @Override
    public void swapCursor(Cursor newCursor) {
        super.swapCursor(newCursor);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtRating;
        private ImageView imgPoster;
        private CardView cardView;
        SimpleViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtRating = itemView.findViewById(R.id.txt_rating);
            imgPoster = itemView.findViewById(R.id.img_poster);
            cardView = itemView.findViewById(R.id.card_movie);
        }
    }
}
