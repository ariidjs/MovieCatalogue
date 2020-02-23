package com.rizkhan.moviecatalogue.fragments;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizkhan.moviecatalogue.BuildConfig;
import com.rizkhan.moviecatalogue.R;
import com.rizkhan.moviecatalogue.adapter.ListMovieAdapter;
import com.rizkhan.moviecatalogue.model.MovieResponse;
import com.rizkhan.moviecatalogue.model.Movies;
import com.rizkhan.moviecatalogue.remote.Client;
import com.rizkhan.moviecatalogue.remote.Server;
import com.rizkhan.moviecatalogue.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListMovieFragment extends Fragment {

    RecyclerView recyclerMovie;
    List<Movies> listMovie;
    private ProgressBar progressBar;
    private ListMovieAdapter adapter;
    private MovieViewModel movieViewModel;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public ListMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_movie, container, false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu3);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                load();
                return true;
            }
        });
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);


        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText == null) {
                        load();
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    showLoading(true);
                    loadSearch(query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadSearch(String title) {
        Server server = Client.getClient().create(Server.class);
        Call<MovieResponse> response = server.getSearchMovies(BuildConfig.GoogleSecAPIKEY, "en-US", title);
        response.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        listMovie = response.body().getResults();
                        adapter.setListMovie(listMovie);
                        showLoading(false);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerMovie = view.findViewById(R.id.recycler_movie);
        progressBar = view.findViewById(R.id.progress_movie);
        showLoading(true);
        listMovie = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(null);
        recyclerMovie.setLayoutManager(linearLayoutManager);
        adapter = new ListMovieAdapter(getContext());
        recyclerMovie.setAdapter(adapter);
        load();
    }

    private void load() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.init();
        movieViewModel.getRepository().observe(this, movieResponse -> {
            if (movieResponse != null) {
                List<Movies> movieList = movieResponse.getResults();
                listMovie.clear();
                listMovie.addAll(movieList);
                adapter.setListMovie(listMovie);
                showLoading(false);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
