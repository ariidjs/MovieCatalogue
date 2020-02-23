package com.rizkhan.moviecatalogue.remote;

import com.rizkhan.moviecatalogue.model.MovieResponse;
import com.rizkhan.moviecatalogue.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Server {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("discover/movie")
    Call<MovieResponse> getUpComingMovies(@Query("api_key") String apiKey,@Query("primary_release_date.gte") String dateGte,@Query("primary_release_date.ite") String dateIte);

    @GET("search/movie")
    Call<MovieResponse> getSearchMovies(@Query("api_key") String apiKey,@Query("language") String language, @Query("query") String search);

    @GET("tv/top_rated")
    Call<TvShowResponse> getTopRatedTv(@Query("api_key") String apiKey);

    @GET("search/tv")
    Call<TvShowResponse> getSearchTv(@Query("api_key") String apiKey,@Query("language") String language, @Query("query") String search);
}
