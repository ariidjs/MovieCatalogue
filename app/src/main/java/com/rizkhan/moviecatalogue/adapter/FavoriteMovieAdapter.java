package com.rizkhan.moviecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rizkhan.moviecatalogue.DetailMovieActivity;
import com.rizkhan.moviecatalogue.R;
import com.rizkhan.moviecatalogue.buildconfig.Config;
import com.rizkhan.moviecatalogue.model.Movies;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {
    private final Context context;
    private Cursor list;
    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    public void setList(Cursor list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_movie, viewGroup, false);
        return new FavoriteMovieAdapter.ViewHolder(view);
    }

    private Movies getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Error");
        }
        return new Movies(list);
    }
    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieAdapter.ViewHolder viewHolder, int i) {
        viewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_anim));
        final Movies movies = getItem(i);
        String image_url = Config.IMAGE_URL_BASE_PATH + movies.getPosterPath();
        Glide.with(context).load(image_url).apply(new RequestOptions().override(350, 550))
                .into(viewHolder.imgPoster);
        viewHolder.txtTitle.setText(movies.getTitle());
        viewHolder.txtRating.setText(String.valueOf(movies.getVoteAverage()));
        viewHolder.cardView.setOnClickListener(v -> {
            Intent intentDetail = new Intent(context, DetailMovieActivity.class);
            intentDetail.putExtra(DetailMovieActivity.EXTRA_MOVIE, movies);
            context.startActivity(intentDetail);
        });
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtRating;
        private ImageView imgPoster;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtRating = itemView.findViewById(R.id.txt_rating);
            imgPoster = itemView.findViewById(R.id.img_poster);
            cardView = itemView.findViewById(R.id.card_movie);
        }
    }
}
