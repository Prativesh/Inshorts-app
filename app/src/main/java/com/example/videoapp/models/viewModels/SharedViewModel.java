package com.example.videoapp.models.viewModels;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.videoapp.models.dataModels.components.Movie;
import com.example.videoapp.repository.AppRepository;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SharedViewModel extends ViewModel {

    private final String LOG_TAG = SharedViewModel.class.getSimpleName();

    private int currentPageFavourites = 1;
    private int currentPageSearched = 1;

    private int totalPageFavourites = 0;
    private int totalPageSearched = 0;

    private final AppRepository repository;

    //Store and show toast for some error
    private final MutableLiveData<Boolean> _showToastMessage;
    public String toastMessage;

    private final MutableLiveData<List<Movie>> _favouriteMovies ;
    private final MutableLiveData<List<Movie>> _searchedMovies;

    //Used to store last query so that if user moves to other tab and comes back, state can be restored
    private final MutableLiveData<String> _savedQuery;

    //Use disposable to manage life cycle and avoid memory leaks
    private final CompositeDisposable disposables;

    SharedViewModel() {
        repository = new AppRepository();
        disposables = new CompositeDisposable();
        _favouriteMovies = new MutableLiveData<>(null);
        _searchedMovies = new MutableLiveData<>(null);
        _showToastMessage = new MutableLiveData<>(false);
        _savedQuery = new MutableLiveData<>("");

        /*Fetching favourite movies as they are required in MovieDetails page which can be opened
        from HomeTab, will show error if no network */
        fetchFavouriteMovies();
    }

    public LiveData<List<Movie>> getFavouriteMovies() {
        return _favouriteMovies;
    }

    public LiveData<List<Movie>> getSearchedMovies() { return _searchedMovies; }

    public LiveData<String> getSavedQuery() { return _savedQuery; }

    public void fetchFavouriteMovies() {
        if(totalPageFavourites == currentPageFavourites){
            return;
        }

        Disposable disposable = repository.getFavouriteMovies(currentPageFavourites)
                .subscribe(
                        response -> {
                            List<Movie> curMovies = _favouriteMovies.getValue() == null
                                    ? new ArrayList<>() : new ArrayList<>(_favouriteMovies.getValue());
                            curMovies.addAll(response.getResults());
                            _favouriteMovies.setValue(curMovies);
                            totalPageFavourites = response.getTotalPages();
                            currentPageFavourites++;
                        },
                        error -> {
                            Log.d(LOG_TAG, "Error fetching Favourite-Movies data: " + error.getMessage());
                            setToastMessage(true, "Something went wrong. Please try again.");
                        }
                );
        disposables.add(disposable);
    }

    public void fetchSearchedMovies(String query, Boolean isNewSearch) {
        if(isNewSearch){
            totalPageSearched = 0;
            currentPageSearched = 1;
        }
        if(totalPageSearched == currentPageSearched) {
            return;
        }

        Disposable disposable = repository.getSearchedMovies(query, currentPageSearched)
            .subscribe(
                response-> {
                    List<Movie> curMovies = _searchedMovies.getValue() == null || currentPageSearched == 1
                            ? new ArrayList<>() : new ArrayList<>(_searchedMovies.getValue());
                    curMovies.addAll(response.getResults());
                    _searchedMovies.setValue(curMovies);
                    _savedQuery.setValue(query);
                    totalPageSearched = response.getTotalPages();
                    currentPageSearched++;
                },
                error -> {
                    Log.d(LOG_TAG, "Error fetchingSearched-Movies data: " + error.getMessage());
                    setToastMessage(true, "Something went wrong. Please try again.");
                }
            );
        disposables.add(disposable);
    }

    public void manageFavouriteMovies(boolean status, Movie movie) {
        Disposable disposable = repository.setFavorite(status, movie.getId())
            .subscribe(
                response -> {
                    /* If api call success add or remove from _favouriteMovies based on status
                    else show a snack bar of something went wrong */
                    if (response.getSuccess()) {
                        List<Movie> currentMovies = _favouriteMovies.getValue() != null
                                ? new ArrayList<>(_favouriteMovies.getValue())
                                : new ArrayList<>();

                        if (status) {
                            currentMovies.add(movie);
                        } else {
                            currentMovies.removeIf(m -> m.getId() == movie.getId());
                        }
                        _favouriteMovies.setValue(currentMovies);
                    } else {
                        Log.d(LOG_TAG, "Error calling Favourite Api: " + response.getStatusMessage());
                        setToastMessage(true, "Something went wrong. Please try again.");
                    }
                },
                error -> {
                    Log.d(LOG_TAG, "Error managing Favourite-Movies data: " + error.getMessage());
                    setToastMessage(true, "Something went wrong. Please try again.");
                }
            );
        disposables.add(disposable);
    }

    private void setToastMessage(Boolean showToast, String message) {
        toastMessage = message;
        _showToastMessage.setValue(showToast);
    }

    //After displaying toast message clear its content and set showToast to false
    public void clearToastMessage() {
        setToastMessage(false, "");
    }

    public LiveData<Boolean> getShowToastMessage() {
        return _showToastMessage;
    }

    public String createMovieDetailDeepLink(Movie movie) throws UnsupportedEncodingException {
        String movieJson = new Gson().toJson(movie);
        String encodedJson = URLEncoder.encode(movieJson, StandardCharsets.UTF_8.toString());

        // Return the deep link with the encoded JSON
        return "https://www.dummy-deep-link.com/movie_detail/" + encodedJson;
    }

}