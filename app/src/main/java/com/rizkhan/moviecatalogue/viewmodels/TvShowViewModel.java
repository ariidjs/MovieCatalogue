package com.rizkhan.moviecatalogue.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rizkhan.moviecatalogue.BuildConfig;
import com.rizkhan.moviecatalogue.model.TvShowResponse;
import com.rizkhan.moviecatalogue.repo.TvShowRepository;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<TvShowResponse> mutableLiveData;
    private TvShowRepository tvShowRepository;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        tvShowRepository = TvShowRepository.getInstance();
        mutableLiveData = tvShowRepository.getTv(BuildConfig.GoogleSecAPIKEY);
    }

    public LiveData<TvShowResponse> getTvRepository() {
        return mutableLiveData;
    }
}
