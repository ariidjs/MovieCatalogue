package com.rizkhan.moviecatalogue.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {
    @SerializedName("results")
    @Expose
    private List<TvShow> tvShowResponses = null;

    public List<TvShow> getResults() {
        return tvShowResponses;
    }

}
