package com.example.videoapp.network.services;

import com.example.videoapp.models.dataModels.api.response.MoviesDefaultResponseModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopRatedMoviesService {
    @GET("movie/top_rated")
    Observable<MoviesDefaultResponseModel> getTopRatedMovies(@Query("page") int page);
}
