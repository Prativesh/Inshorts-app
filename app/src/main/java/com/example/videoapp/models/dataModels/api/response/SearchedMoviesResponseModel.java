package com.example.videoapp.models.dataModels.api.response;

import com.example.videoapp.models.dataModels.components.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchedMoviesResponseModel {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Movie> results;

    @SerializedName("total_pages")
    private int totalPages;

    public List<Movie> getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
