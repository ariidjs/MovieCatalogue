package com.rizkhan.moviecatalogue.fragments;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizkhan.moviecatalogue.R;
import com.rizkhan.moviecatalogue.adapter.FavoriteTvAdapter;
import com.rizkhan.moviecatalogue.db.TvShowHelper;

import static com.rizkhan.moviecatalogue.db.TvShowContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment {

    private RecyclerView recyclerView;
    private Cursor tvShowList;
    private FavoriteTvAdapter adapter;
    private TvShowHelper tvShowHelper;
    TextView txtDescTv;

    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_tv_fav);
        txtDescTv = view.findViewById(R.id.txtDescription_tv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FavoriteTvAdapter(getContext());
        recyclerView.setAdapter(adapter);
        tvShowHelper = new TvShowHelper(getContext());
        tvShowHelper.open();
        new load().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        new load().execute();
    }

    private class load extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor list) {
            super.onPostExecute(list);
            tvShowList = list;
            adapter.setList(tvShowList);

            if (tvShowList.getCount() == 0) {
                recyclerView.setVisibility(View.GONE);
                txtDescTv.setVisibility(View.VISIBLE);
            }else {
                recyclerView.setVisibility(View.VISIBLE);
                txtDescTv.setVisibility(View.GONE);
            }
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI, null, null,
                    null, null);
        }
    }
}
