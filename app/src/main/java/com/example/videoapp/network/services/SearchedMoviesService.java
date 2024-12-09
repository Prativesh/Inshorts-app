package com.example.videoapp.network.services;

import com.example.videoapp.models.dataModels.api.response.SearchedMoviesResponseModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchedMoviesService {

    @GET("search/movie")
    Observable<SearchedMoviesResponseModel> getSearchedMovies(
            @Query("query") String query,
            @Query("include_adult") Boolean includeAdult,
            @Query("language") String language,
            @Query("page") int page
    );
}
