package com.example.videoapp.network.services;

import com.example.videoapp.models.dataModels.api.request.ManageFavouriteMoviesRequestModel;
import com.example.videoapp.models.dataModels.api.response.MoviesDefaultResponseModel;
import com.example.videoapp.models.dataModels.api.response.ManageFavouriteMoviesResponseModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FavouriteMoviesService {

    @GET("account/21667367/favorite/movies")
    Observable<MoviesDefaultResponseModel> getFavouriteMovies(@Query("page") int page);

    @POST("account/21667367/favorite")
    Observable<ManageFavouriteMoviesResponseModel> setFavouriteMovie(
            @Body ManageFavouriteMoviesRequestModel request);
}
