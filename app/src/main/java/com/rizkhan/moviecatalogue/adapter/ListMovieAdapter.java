package com.rizkhan.moviecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListViewHolder> {
    private final Context context;
    private List<Movies> listMovie = new ArrayList<>();

    public ListMovieAdapter(Context context) {
        this.context = context;
    }

    public void setListMovie(List<Movies> listMovie) {
        this.listMovie = listMovie;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_movie, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {
        listViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_anim));
        final Movies movies = listMovie.get(listViewHolder.getAdapterPosition());
        String image_url = Config.IMAGE_URL_BASE_PATH + movies.getPosterPath();
        Glide.with(context).load(image_url).apply(new RequestOptions().override(350, 550))
                .into(listViewHolder.imgPoster);
        listViewHolder.txtTitle.setText(movies.getTitle());
        listViewHolder.txtRating.setText(String.valueOf(movies.getVoteAverage()));
        listViewHolder.cardView.setOnClickListener(v -> {
            Intent intentDetail = new Intent(context, DetailMovieActivity.class);
            intentDetail.putExtra(DetailMovieActivity.EXTRA_MOVIE, movies);
            context.startActivity(intentDetail);
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtRating;
        private ImageView imgPoster;
        private CardView cardView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtRating = itemView.findViewById(R.id.txt_rating);
            imgPoster = itemView.findViewById(R.id.img_poster);
            cardView = itemView.findViewById(R.id.card_movie);
        }
    }
}
