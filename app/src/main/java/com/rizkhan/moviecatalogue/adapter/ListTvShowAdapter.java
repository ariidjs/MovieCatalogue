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
import com.rizkhan.moviecatalogue.DetailTvShowActivity;
import com.rizkhan.moviecatalogue.R;
import com.rizkhan.moviecatalogue.buildconfig.Config;
import com.rizkhan.moviecatalogue.model.TvShow;

import java.util.ArrayList;
import java.util.List;

public class ListTvShowAdapter extends RecyclerView.Adapter<ListTvShowAdapter.TvViewHolder> {
    private final Context context;
    private List<TvShow> listTVShows = new ArrayList<>();

    public ListTvShowAdapter(Context context) {
        this.context = context;
    }

    public void setListTVShows(List<TvShow> listTVShows) {
        this.listTVShows = listTVShows;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_tvshow, viewGroup, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder tvViewHolder, int i) {
        tvViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_anim));
        final TvShow tvShow = listTVShows.get(tvViewHolder.getAdapterPosition());
        String image_url = Config.IMAGE_URL_BASE_PATH + tvShow.getPosterPath();
        Glide.with(context).load(image_url).apply(new RequestOptions().override(350, 550))
                .into(tvViewHolder.imageTv);
        tvViewHolder.txtTitle.setText(tvShow.getOriginalName());
        tvViewHolder.txtTgl.setText(tvShow.getFirstAirDate());
        tvViewHolder.txtRating.setText(String.valueOf(tvShow.getVoteAverage()));
        tvViewHolder.cardView.setOnClickListener(v -> {
            Intent intentDetailTv = new Intent(context, DetailTvShowActivity.class);
            intentDetailTv.putExtra(DetailTvShowActivity.EXTRA_DETAIL, tvShow);
            context.startActivity(intentDetailTv);
        });
    }

    @Override
    public int getItemCount() {
        return listTVShows.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtTgl, txtRating;
        private ImageView imageTv;
        private CardView cardView;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_tv_title);
            txtTgl = itemView.findViewById(R.id.txt_tv_tgl);
            txtRating = itemView.findViewById(R.id.txt_tv_rating);
            imageTv = itemView.findViewById(R.id.img_tv_show);
            cardView = itemView.findViewById(R.id.card_tv_show);
        }
    }
}
