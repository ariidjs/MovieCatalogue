package com.rizkhan.moviecatalogue.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rizkhan.moviecatalogue.BuildConfig;
import com.rizkhan.moviecatalogue.model.MovieResponse;
import com.rizkhan.moviecatalogue.repo.MovieRepository;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<MovieResponse> mutableLiveData;
    private MovieRepository movieRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        movieRepository = MovieRepository.getInstance();
        mutableLiveData = movieRepository.getMovie(BuildConfig.GoogleSecAPIKEY);

    }

    public LiveData<MovieResponse> getRepository() {
        return mutableLiveData;
    }
}
