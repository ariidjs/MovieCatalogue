package com.rizkhan.moviecatalogue.repo;

import androidx.lifecycle.MutableLiveData;

import com.rizkhan.moviecatalogue.model.MovieResponse;
import com.rizkhan.moviecatalogue.remote.Client;
import com.rizkhan.moviecatalogue.remote.Server;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private static MovieRepository movieRepository;
    private Server server;

    public static MovieRepository getInstance() {
        if (movieRepository == null) {
            movieRepository = new MovieRepository();
        }
        return movieRepository;
    }

    public MutableLiveData<MovieResponse> getMovie(String apiKey) {
        final MutableLiveData<MovieResponse> movieData = new MutableLiveData<>();
        server = Client.getClient().create(Server.class);
        Call<MovieResponse> response = server.getTopRatedMovies(apiKey);
        response.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        movieData.setValue(response.body());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                movieData.setValue(null);
                t.printStackTrace();
            }
        });
        return movieData;
    }

}
