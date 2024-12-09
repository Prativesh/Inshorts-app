package com.example.videoapp.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.example.videoapp.database.AppDatabase;
import com.example.videoapp.database.dataAccessObject.NowPlayingMoviesDAO;
import com.example.videoapp.database.dataAccessObject.TopRatedMoviesDAO;
import com.example.videoapp.models.dataModels.api.request.ManageFavouriteMoviesRequestModel;
import com.example.videoapp.models.dataModels.api.response.MoviesDefaultResponseModel;
import com.example.videoapp.models.dataModels.api.response.ManageFavouriteMoviesResponseModel;
import com.example.videoapp.models.dataModels.api.response.NowPlayingMoviesResponseModel;
import com.example.videoapp.models.dataModels.api.response.SearchedMoviesResponseModel;
import com.example.videoapp.network.RetrofitInstance;
import com.example.videoapp.network.services.FavouriteMoviesService;
import com.example.videoapp.network.services.NowPlayingService;
import com.example.videoapp.network.services.TopRatedMoviesService;
import com.example.videoapp.network.services.SearchedMoviesService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppRepository {

    private final FavouriteMoviesService favouriteMoviesApiService;
    private final SearchedMoviesService searchedMoviesService;

    public AppRepository() {
        favouriteMoviesApiService = RetrofitInstance.createService(FavouriteMoviesService.class);
        searchedMoviesService = RetrofitInstance.createService(SearchedMoviesService.class);
    }

    public Observable<MoviesDefaultResponseModel> getFavouriteMovies(int page) {
        return favouriteMoviesApiService.getFavouriteMovies(page)
                .subscribeOn(Schedulers.io()) //Always perform query on background thread
                .observeOn(AndroidSchedulers.mainThread()); //Observe on main thread
    }

    public Observable<ManageFavouriteMoviesResponseModel> setFavorite(boolean status, int id) {
        ManageFavouriteMoviesRequestModel requestModel =
                new ManageFavouriteMoviesRequestModel("movie", id, status);
        return favouriteMoviesApiService.setFavouriteMovie(requestModel)
                .subscribeOn(Schedulers.io()) //Always perform query on background thread
                .observeOn(AndroidSchedulers.mainThread()); //Observe on main thread
    }

    public Observable<SearchedMoviesResponseModel> getSearchedMovies(String query, int page) {
        return searchedMoviesService.getSearchedMovies(query, false, "en-US", page)
                .subscribeOn(Schedulers.io()) //Always perform query on background thread
                .observeOn(AndroidSchedulers.mainThread()); //Observe on main thread
    }
}
