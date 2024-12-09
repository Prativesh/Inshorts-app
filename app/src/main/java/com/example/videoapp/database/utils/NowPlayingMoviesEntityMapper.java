package com.example.videoapp.database.utils;

import com.example.videoapp.database.entities.NowPlayingMoviesEntity;
import com.example.videoapp.models.dataModels.components.Movie;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingMoviesEntityMapper {

    private static NowPlayingMoviesEntity mapToNowPlayingMoviesEntity(Movie movie) {
        NowPlayingMoviesEntity nowPlayingMoviesEntity = new NowPlayingMoviesEntity();
        nowPlayingMoviesEntity.id = movie.getId();
        nowPlayingMoviesEntity.posterPath = movie.getPosterPath();
        nowPlayingMoviesEntity.backdropPath = movie.getBackdropPath();
        nowPlayingMoviesEntity.title = movie.getTitle();
        nowPlayingMoviesEntity.releaseDate = movie.getReleaseDate();
        nowPlayingMoviesEntity.originalLanguage = movie.getOriginalLanguage();
        nowPlayingMoviesEntity.overview = movie.getOverview();
        nowPlayingMoviesEntity.genreIds = movie.getGenreIds();
        nowPlayingMoviesEntity.voteAverage = movie.getVoteAverage();
        nowPlayingMoviesEntity.popularity = movie.getPopularity();

        return nowPlayingMoviesEntity;
    }

    private static Movie mapToNowPlayingMovie(NowPlayingMoviesEntity nowPlayingMoviesEntity) {
        Movie movie = new Movie();
        movie.setId(nowPlayingMoviesEntity.id);
        movie.setPosterPath(nowPlayingMoviesEntity.posterPath);
        movie.setBackdropPath(nowPlayingMoviesEntity.backdropPath);
        movie.setTitle(nowPlayingMoviesEntity.title);
        movie.setReleaseDate(nowPlayingMoviesEntity.releaseDate);
        movie.setOriginalLanguage(nowPlayingMoviesEntity.originalLanguage);
        movie.setOverview(nowPlayingMoviesEntity.overview);
        movie.setGenreIds(nowPlayingMoviesEntity.genreIds);
        movie.setVoteAverage(nowPlayingMoviesEntity.voteAverage);
        movie.setPopularity(nowPlayingMoviesEntity.popularity);

        return movie;
    }

    public static List<NowPlayingMoviesEntity> mapListToNowPlayingMoviesEntities(List<Movie> movies) {
        List<NowPlayingMoviesEntity> entities = new ArrayList<>();

        for(Movie movie: movies){
            entities.add(NowPlayingMoviesEntityMapper.mapToNowPlayingMoviesEntity(movie));
        }
        return entities;
    }

    public static List<Movie> mapListToNowPlayingMovies(List<NowPlayingMoviesEntity> entities) {
        List<Movie> movies = new ArrayList<>();

        for(NowPlayingMoviesEntity nowPlayingMoviesEntity: entities){
            movies.add(NowPlayingMoviesEntityMapper.mapToNowPlayingMovie(nowPlayingMoviesEntity));
        }

        return movies;
    }
}
