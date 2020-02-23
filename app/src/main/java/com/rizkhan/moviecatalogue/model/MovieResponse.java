package com.rizkhan.moviecatalogue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    @Expose
    private List<Movies> results = null;


    public List<Movies> getResults() {
        return results;
    }
}
