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
import com.rizkhan.moviecatalogue.adapter.ListTvShowAdapter;
import com.rizkhan.moviecatalogue.model.TvShow;
import com.rizkhan.moviecatalogue.model.TvShowResponse;
import com.rizkhan.moviecatalogue.remote.Client;
import com.rizkhan.moviecatalogue.remote.Server;
import com.rizkhan.moviecatalogue.viewmodels.TvShowViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListTvShowFragment extends Fragment {

    RecyclerView recyclerView;
    private ListTvShowAdapter tvShowAdapter;
    private ProgressBar progressTv;
    private List<TvShow> tv_show;
    private TvShowViewModel tvShowViewModel;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public ListTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_tv_show, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_tv_show);
        progressTv = view.findViewById(R.id.progress_tv);
        showLoading(true);
        tv_show = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvShowAdapter = new ListTvShowAdapter(getContext());
        recyclerView.setAdapter(tvShowAdapter);
        loadTv();

    }

    private void loadTv() {
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.init();
        tvShowViewModel.getTvRepository().observe(this, tvResponse -> {
            if (tvResponse != null) {
                List<TvShow> tvShows = tvResponse.getResults();
                tv_show.clear();
                tv_show.addAll(tvShows);
                tvShowAdapter.setListTVShows(tv_show);
                showLoading(false);
            }
        });
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
                loadTv();
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
                        loadTv();
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String title) {
                    showLoading(true);
                    loadSearchTv(title);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadSearchTv(String title) {
        Server server = Client.getClient().create(Server.class);
        Call<TvShowResponse> response = server.getSearchTv(BuildConfig.GoogleSecAPIKEY, "en-US", title);
        response.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        tv_show = response.body().getResults();
                        tvShowAdapter.setListTVShows(tv_show);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressTv.setVisibility(View.VISIBLE);
        } else {
            progressTv.setVisibility(View.GONE);
        }
    }

}
