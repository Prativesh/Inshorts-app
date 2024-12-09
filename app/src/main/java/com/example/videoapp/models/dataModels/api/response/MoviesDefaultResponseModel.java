package com.example.videoapp.models.dataModels.api.response;

import com.example.videoapp.models.dataModels.components.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesDefaultResponseModel {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
