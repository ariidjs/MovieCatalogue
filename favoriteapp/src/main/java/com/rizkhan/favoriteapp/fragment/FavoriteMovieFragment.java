package com.rizkhan.favoriteapp.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizkhan.favoriteapp.R;
import com.rizkhan.favoriteapp.adapter.MovieFavoriteAdapter;

import static com.rizkhan.favoriteapp.db.MovieContract.CONTENT_URI;

public class FavoriteMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOAD_MOVIE_ID = 1001;
    RecyclerView recyclerMovie;
    private MovieFavoriteAdapter adapter;
    public FavoriteMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(null);
        recyclerMovie.setLayoutManager(linearLayoutManager);
        adapter = new MovieFavoriteAdapter(getActivity());
        recyclerMovie.setAdapter(adapter);
        getLoaderManager().initLoader(LOAD_MOVIE_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getContext(), CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
