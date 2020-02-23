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
import com.rizkhan.moviecatalogue.adapter.FavoriteMovieAdapter;
import com.rizkhan.moviecatalogue.db.MovieHelper;

import static com.rizkhan.moviecatalogue.db.MovieContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment {

    private RecyclerView recyclerMovie;
    private TextView txtDesc;
    Cursor listMovie;
    private FavoriteMovieAdapter adapter;
    private MovieHelper movieHelper;
    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerMovie = view.findViewById(R.id.recycler_movie_fav);
        txtDesc = view.findViewById(R.id.txtDescription);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(null);
        recyclerMovie.setLayoutManager(linearLayoutManager);
        adapter = new FavoriteMovieAdapter(getContext());
        recyclerMovie.setAdapter(adapter);
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
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
            listMovie = list;
            adapter.setList(listMovie);
            adapter.notifyDataSetChanged();

            if (listMovie.getCount() == 0) {
                recyclerMovie.setVisibility(View.GONE);
                txtDesc.setVisibility(View.VISIBLE);
            }else {
                recyclerMovie.setVisibility(View.VISIBLE);
                txtDesc.setVisibility(View.GONE);
            }
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI, null, null,
                    null, null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new load().execute();
    }
}
