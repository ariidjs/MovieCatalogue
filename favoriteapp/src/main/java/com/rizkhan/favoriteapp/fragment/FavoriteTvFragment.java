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
import com.rizkhan.favoriteapp.adapter.FavoriteTvAdapter;

import static com.rizkhan.favoriteapp.db.TvShowContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOAD_TV_ID = 2001;
    private RecyclerView recyclerView;
    private FavoriteTvAdapter adapter;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FavoriteTvAdapter(getContext());
        recyclerView.setAdapter(adapter);

        getLoaderManager().initLoader(LOAD_TV_ID, null, this);
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
