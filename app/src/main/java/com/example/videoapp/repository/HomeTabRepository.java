package com.example.videoapp.repository;

import com.example.videoapp.database.AppDatabase;
import com.example.videoapp.database.dataAccessObject.NowPlayingMoviesDAO;
import com.example.videoapp.database.dataAccessObject.TopRatedMoviesDAO;
import com.example.videoapp.database.entities.NowPlayingMoviesEntity;
import com.example.videoapp.database.entities.TopRatedMoviesEntity;
import com.example.videoapp.database.utils.NowPlayingMoviesEntityMapper;
import com.example.videoapp.database.utils.TopRatedMoviesEntityMapper;
import com.example.videoapp.models.dataModels.api.response.MoviesDefaultResponseModel;
import com.example.videoapp.models.dataModels.api.response.NowPlayingMoviesResponseModel;
import com.example.videoapp.models.dataModels.components.Movie;
import com.example.videoapp.network.RetrofitInstance;
import com.example.videoapp.network.services.NowPlayingService;
import com.example.videoapp.network.services.TopRatedMoviesService;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeTabRepository {

    private final NowPlayingService nowPlayingApiService;
    private final TopRatedMoviesService topRatedMoviesService;

    private static TopRatedMoviesDAO topRatedMoviesDAO;
    private static NowPlayingMoviesDAO nowPlayingMoviesDAO;

    public HomeTabRepository() {
        AppDatabase appDatabase = AppDatabase.getInstance();
        topRatedMoviesDAO = appDatabase.topRatedMoviesDAO();
        nowPlayingMoviesDAO = appDatabase.nowPlayingMoviesDAO();
        nowPlayingApiService = RetrofitInstance.createService(NowPlayingService.class);
        topRatedMoviesService = RetrofitInstance.createService(TopRatedMoviesService.class);
    }

    public Observable<MoviesDefaultResponseModel> getTopRatedMovies(int page) {
        return topRatedMoviesService.getTopRatedMovies(page)
                .subscribeOn(Schedulers.io()) //Always perform query on background thread
                .doOnNext(
                        response -> {
                            List<TopRatedMoviesEntity> entities =
                                    TopRatedMoviesEntityMapper.mapListToTopRatedMoviesEntities(response.getResults());
                            if(page == 1) { //Clear previous table and store fresh data
                                topRatedMoviesDAO.deleteAllTopRatedMovies();
                            }
                            topRatedMoviesDAO.insertTopRatedMovies(entities);
                        }
                )
                .observeOn(AndroidSchedulers.mainThread()); //Observe on main thread
    }

    public Observable<NowPlayingMoviesResponseModel> getNowPlayingMovies(int page) {
        return nowPlayingApiService.getNowPlayingMovies(page)
                .subscribeOn(Schedulers.io()) //Always perform query on background thread
                .doOnNext(
                        response -> {
                            List<NowPlayingMoviesEntity> entities =
                                    NowPlayingMoviesEntityMapper.mapListToNowPlayingMoviesEntities(response.getResults());
                            if(page == 1){ //Clear previous table and store fresh data
                                nowPlayingMoviesDAO.deleteAllNowPlayingMovies();
                            }
                            nowPlayingMoviesDAO.insertNowPlayingMovies(entities);
                        }
                )
                .observeOn(AndroidSchedulers.mainThread()); //Observe on main thread
    }

    public Observable<List<Movie>> getTopRatedMoviesFromDB() {
        return Observable.fromCallable(() -> {
                    //Always fetch the entities from the database on a background thread
                    List<TopRatedMoviesEntity> entities = topRatedMoviesDAO.getTopRatedMovies();

                    return TopRatedMoviesEntityMapper.mapListToTopRatedMovies(entities);
                })
                .subscribeOn(Schedulers.io()) //Perform database query on background thread
                .observeOn(AndroidSchedulers.mainThread()); //Observe the result on the main thread
    }

    public Observable<List<Movie>> getNowPlayingMoviesFromDB() {
        return Observable.fromCallable(() -> {
                    //Always fetch the entities from the database on a background thread
                    List<NowPlayingMoviesEntity> entities = nowPlayingMoviesDAO.getNowPlayingMovies();

                    return NowPlayingMoviesEntityMapper.mapListToNowPlayingMovies(entities);
                })
                .subscribeOn(Schedulers.io()) //Perform database query on background thread
                .observeOn(AndroidSchedulers.mainThread()); //Observe the result on the main thread
    }
}
