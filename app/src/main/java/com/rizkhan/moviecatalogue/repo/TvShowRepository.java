package com.rizkhan.moviecatalogue.repo;

import androidx.lifecycle.MutableLiveData;

import com.rizkhan.moviecatalogue.model.TvShowResponse;
import com.rizkhan.moviecatalogue.remote.Client;
import com.rizkhan.moviecatalogue.remote.Server;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowRepository {
    private static TvShowRepository tvShowRepository;
    private Server tvShowServer;

    public static TvShowRepository getInstance() {
        if (tvShowRepository == null) {
            tvShowRepository = new TvShowRepository();
        }
        return tvShowRepository;
    }

    public MutableLiveData<TvShowResponse> getTv(String apiKey) {
        final MutableLiveData<TvShowResponse> tvData = new MutableLiveData<>();
        tvShowServer = Client.getClient().create(Server.class);
        Call<TvShowResponse> response = tvShowServer.getTopRatedTv(apiKey);
        response.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        tvData.setValue(response.body());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                tvData.setValue(null);
                t.printStackTrace();
            }
        });
        return tvData;
    }
}
