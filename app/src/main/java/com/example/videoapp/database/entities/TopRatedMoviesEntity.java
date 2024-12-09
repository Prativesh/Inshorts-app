package com.example.videoapp.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.videoapp.database.Converter;
import java.util.List;

@Entity(tableName = "top_rated_movies")
@TypeConverters(Converter.class)
public class TopRatedMoviesEntity {

    @PrimaryKey(autoGenerate = true)
    public int insertOrder;

    public int id;

    @ColumnInfo(name = "poster_path")
    public String posterPath;

    @ColumnInfo(name = "backdrop_path")
    public String backdropPath;
    public String title;

    @ColumnInfo(name = "release_date")
    public String releaseDate;

    @ColumnInfo(name = "language")
    public String originalLanguage;
    public String overview;

    @ColumnInfo(name = "genres")
    public List<Integer> genreIds;
    @ColumnInfo(name = "vote_average")
    public Double voteAverage;

    public Double popularity;

}
