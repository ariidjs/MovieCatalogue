package com.rizkhan.favoriteapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rizkhan.favoriteapp.R;
import com.rizkhan.favoriteapp.buildconfig.Config;

import static com.rizkhan.favoriteapp.db.TvShowContract.TvShowEntry.POSTER_PATH;
import static com.rizkhan.favoriteapp.db.TvShowContract.TvShowEntry.RELEASE_DATE;
import static com.rizkhan.favoriteapp.db.TvShowContract.TvShowEntry.TITLE;
import static com.rizkhan.favoriteapp.db.TvShowContract.TvShowEntry.USER_RATING;
import static com.rizkhan.favoriteapp.db.TvShowContract.getColumnString;

public class FavoriteTvAdapter extends CursorRecyclerAdapter<FavoriteTvAdapter.ViewHolder> {
    private Context mContext;

    public FavoriteTvAdapter(Context context) {
        super(null);
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_tvshow, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        String image_url = Config.IMAGE_URL_BASE_PATH + getColumnString(cursor,POSTER_PATH);
        String title = getColumnString(cursor,TITLE);
        String tgl = getColumnString(cursor,RELEASE_DATE);
        String rating = getColumnString(cursor,USER_RATING);
        Glide.with(mContext).load(image_url).apply(new RequestOptions().override(350, 550))
                .into(viewHolder.imageTv);
        viewHolder.txtTitle.setText(title);
        viewHolder.txtTgl.setText(tgl);
        viewHolder.txtRating.setText(rating);
//        viewHolder.cardView.setOnClickListener(v -> {
//            Intent intentDetailTv = new Intent(context, DetailTvShowActivity.class);
//            intentDetailTv.putExtra(DetailTvShowActivity.EXTRA_DETAIL, tvShow);
//            context.startActivity(intentDetailTv);
//        });
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
