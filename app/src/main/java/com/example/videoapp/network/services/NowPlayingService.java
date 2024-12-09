package com.example.videoapp.network.services;

import com.example.videoapp.models.dataModels.api.response.NowPlayingMoviesResponseModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NowPlayingService {
    @GET("movie/now_playing")
    Observable<NowPlayingMoviesResponseModel> getNowPlayingMovies(@Query("page") int page);
}
