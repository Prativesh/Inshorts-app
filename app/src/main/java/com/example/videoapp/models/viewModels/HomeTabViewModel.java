package com.example.videoapp.models.viewModels;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.videoapp.models.dataModels.components.Movie;
import com.example.videoapp.repository.HomeTabRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class HomeTabViewModel extends ViewModel {

    private final String LOG_TAG = HomeTabViewModel.class.getSimpleName();

    private final HomeTabRepository repository;
    private int currentPageNowPlaying = 1;
    private int currentPageTopRated = 1;
    private int totalPageNowPlaying = 0;
    private int totalPageTopRated = 0;

    private final MutableLiveData<List<Movie>> _nowPlayingMovies;
    private final MutableLiveData<List<Movie>> _topRatedMovies;

    //For state management in pull to refresh
    private final MutableLiveData<Boolean> _isLoading;

    //Using two boolean's, one for each topRated and NowPlaying movies.
    //Will show error screen only if both API calls fail and no data in local DB
    private final MutableLiveData<Pair<Boolean, Boolean>> _errorState;

    //Use disposable to manage life cycle and avoid memory leaks
    private final CompositeDisposable disposables;


    public HomeTabViewModel() {
        repository = new HomeTabRepository();
        _topRatedMovies = new MutableLiveData<>(null);
        _nowPlayingMovies = new MutableLiveData<>(null);
        _isLoading = new MutableLiveData<>(false);
        _errorState = new MutableLiveData<>(new Pair<>(false, false));
        disposables = new CompositeDisposable();
    }

    public LiveData<List<Movie>> getNowPlayingMovies() { return _nowPlayingMovies; }

    public LiveData<List<Movie>> getTopRatedMovies() { return _topRatedMovies; }

    public LiveData<Pair<Boolean, Boolean>> getErrorState() { return _errorState; }

    public LiveData<Boolean> getIsLoading() { return _isLoading; }

    public void refreshHomePage() {
        _isLoading.setValue(true);

        // Reset current pages and clear movie lists
        currentPageNowPlaying = 1;
        currentPageTopRated = 1;
        _nowPlayingMovies.setValue(null);
        _topRatedMovies.setValue(null);

        // Fetch data again
        fetchNowPlayingMovies();
        fetchTopRatedMovies();
    }

    public void fetchNowPlayingMovies() {
        if (totalPageNowPlaying == currentPageNowPlaying){
            return;
        }

        Disposable disposableApi = repository.getNowPlayingMovies(currentPageNowPlaying)
                .subscribe(
                        response -> {
                            List<Movie> curMovies = _nowPlayingMovies.getValue() == null
                                    ? new ArrayList<>() : new ArrayList<>(_nowPlayingMovies.getValue());
                            curMovies.addAll(response.getResults());
                            _nowPlayingMovies.setValue(curMovies);

                            //Clear any previous error for nowPlaying list
                            _errorState.setValue(new Pair<>(false, Objects.requireNonNull(_errorState.getValue()).second));

                            //Update total pages
                            totalPageNowPlaying = response.getTotalPages();

                            currentPageNowPlaying++;
                            _isLoading.setValue(false);
                        },
                        error -> {
                            Log.d(LOG_TAG, "Error fetching Now-Playing-Movies data: " + error.getMessage());

                            // If network call fails, fetch data from DB
                            Disposable disposableDb = repository.getNowPlayingMoviesFromDB()
                                    .subscribe(
                                            moviesFromDb -> {
                                                if (moviesFromDb != null && !moviesFromDb.isEmpty()) {
                                                    _nowPlayingMovies.setValue(moviesFromDb);
                                                } else {
                                                    //Api call failed and no data in DB
                                                    _errorState.setValue(new Pair<>(true, Objects.requireNonNull(_errorState.getValue()).second));
                                                }
                                                _isLoading.setValue(false);
                                            },
                                            dbError -> { //DB read failed
                                                Log.d(LOG_TAG, "Error fetching Now-Playing-Movies from DB: " + dbError.getMessage());
                                                _errorState.setValue(new Pair<>(true, Objects.requireNonNull(_errorState.getValue()).second));
                                                _isLoading.setValue(false);
                                            }
                                    );
                            disposables.add(disposableDb);
                        }
                );
        disposables.add(disposableApi);
    }

    public void fetchTopRatedMovies() {
        if(totalPageTopRated == currentPageTopRated) {
            return;
        }

        Disposable disposableApi = repository.getTopRatedMovies(currentPageTopRated)
                .subscribe(
                        response -> {
                            List<Movie> curMovies = _topRatedMovies.getValue() == null
                                    ? new ArrayList<>() : new ArrayList<>(_topRatedMovies.getValue());
                            curMovies.addAll(response.getResults());
                            _topRatedMovies.setValue(curMovies);

                            //Clear any previous error for topRated list
                            _errorState.setValue(new Pair<>(Objects.requireNonNull(_errorState.getValue()).first, false));

                            //Update total pages
                            totalPageTopRated = response.getTotalPages();

                            currentPageTopRated++;
                            _isLoading.setValue(false);
                        },
                        error -> {
                            Log.d(LOG_TAG, "Error fetching Top-Rated-Movies data: " + error.getMessage());

                            // If network call fails, fetch data from DB
                            Disposable disposableDb = repository.getTopRatedMoviesFromDB()
                                    .subscribe(
                                            moviesFromDb -> {
                                                if (moviesFromDb != null && !moviesFromDb.isEmpty()) {
                                                    _topRatedMovies.setValue(moviesFromDb);
                                                } else {
                                                    //Api call failed and no data in DB
                                                    _errorState.setValue(new Pair<>(Objects.requireNonNull(_errorState.getValue()).first, true));
                                                }
                                                _isLoading.setValue(false);
                                            },
                                            dbError -> {//DB read failed
                                                Log.d(LOG_TAG, "Error fetching Top-Rated-Movies from DB: " + dbError.getMessage());
                                                _errorState.setValue(new Pair<>(Objects.requireNonNull(_errorState.getValue()).first, true));
                                                _isLoading.setValue(false);
                                            }
                                    );
                            disposables.add(disposableDb);
                        }
                );
        disposables.add(disposableApi);
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear(); // Avoid memory leaks
    }
}
