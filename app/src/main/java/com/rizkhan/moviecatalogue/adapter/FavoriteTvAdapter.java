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
import com.rizkhan.moviecatalogue.DetailTvShowActivity;
import com.rizkhan.moviecatalogue.R;
import com.rizkhan.moviecatalogue.buildconfig.Config;
import com.rizkhan.moviecatalogue.model.TvShow;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder> {
    private final Context context;
    private Cursor list;

    public FavoriteTvAdapter(Context context) {
        this.context = context;
    }

    public void setList(Cursor list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_tvshow, viewGroup, false);
        return new ViewHolder(view);
    }

    private TvShow getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Error");
        }
        return new TvShow(list);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_anim));
        final TvShow tvShow = getItem(viewHolder.getAdapterPosition());
        String image_url = Config.IMAGE_URL_BASE_PATH + tvShow.getPosterPath();
        Glide.with(context).load(image_url).apply(new RequestOptions().override(350, 550))
                .into(viewHolder.imageTv);
        viewHolder.txtTitle.setText(tvShow.getOriginalName());
        viewHolder.txtTgl.setText(tvShow.getFirstAirDate());
        viewHolder.txtRating.setText(String.valueOf(tvShow.getVoteAverage()));
        viewHolder.cardView.setOnClickListener(v -> {
            Intent intentDetailTv = new Intent(context, DetailTvShowActivity.class);
            intentDetailTv.putExtra(DetailTvShowActivity.EXTRA_DETAIL, tvShow);
            context.startActivity(intentDetailTv);
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
        private TextView txtTitle, txtTgl, txtRating;
        private ImageView imageTv;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_tv_title);
            txtTgl = itemView.findViewById(R.id.txt_tv_tgl);
            txtRating = itemView.findViewById(R.id.txt_tv_rating);
            imageTv = itemView.findViewById(R.id.img_tv_show);
            cardView = itemView.findViewById(R.id.card_tv_show);
        }
    }
}
