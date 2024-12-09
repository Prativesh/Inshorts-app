package com.example.videoapp.database.utils;

import com.example.videoapp.database.entities.TopRatedMoviesEntity;
import com.example.videoapp.models.dataModels.components.Movie;
import java.util.ArrayList;
import java.util.List;

public class TopRatedMoviesEntityMapper {

    private static TopRatedMoviesEntity mapToTopRatedMoviesEntity(Movie movie) {
        TopRatedMoviesEntity topRatedMoviesEntity = new TopRatedMoviesEntity();
        topRatedMoviesEntity.id = movie.getId();
        topRatedMoviesEntity.posterPath = movie.getPosterPath();
        topRatedMoviesEntity.backdropPath = movie.getBackdropPath();
        topRatedMoviesEntity.title = movie.getTitle();
        topRatedMoviesEntity.releaseDate = movie.getReleaseDate();
        topRatedMoviesEntity.originalLanguage = movie.getOriginalLanguage();
        topRatedMoviesEntity.overview = movie.getOverview();
        topRatedMoviesEntity.genreIds = movie.getGenreIds();
        topRatedMoviesEntity.voteAverage = movie.getVoteAverage();
        topRatedMoviesEntity.popularity = movie.getPopularity();

        return topRatedMoviesEntity;
    }

    private static Movie mapToTopRatedMovie(TopRatedMoviesEntity topRatedMoviesEntity) {
        Movie movie = new Movie();
        movie.setId(topRatedMoviesEntity.id);
        movie.setPosterPath(topRatedMoviesEntity.posterPath);
        movie.setBackdropPath(topRatedMoviesEntity.backdropPath);
        movie.setTitle(topRatedMoviesEntity.title);
        movie.setReleaseDate(topRatedMoviesEntity.releaseDate);
        movie.setOriginalLanguage(topRatedMoviesEntity.originalLanguage);
        movie.setOverview(topRatedMoviesEntity.overview);
        movie.setGenreIds(topRatedMoviesEntity.genreIds);
        movie.setVoteAverage(topRatedMoviesEntity.voteAverage);
        movie.setPopularity(topRatedMoviesEntity.popularity);
        return movie;
    }

    public static List<TopRatedMoviesEntity> mapListToTopRatedMoviesEntities(List<Movie> movies) {
        List<TopRatedMoviesEntity> entities = new ArrayList<>();

        for(Movie movie: movies){
            entities.add(TopRatedMoviesEntityMapper.mapToTopRatedMoviesEntity(movie));
        }
        return entities;
    }

    public static List<Movie> mapListToTopRatedMovies(List<TopRatedMoviesEntity> entities) {
        List<Movie> movies = new ArrayList<>();

        for(TopRatedMoviesEntity topRatedMoviesEntity: entities){
            movies.add(TopRatedMoviesEntityMapper.mapToTopRatedMovie(topRatedMoviesEntity));
        }

        return movies;
    }

}
