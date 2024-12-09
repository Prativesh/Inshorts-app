package com.example.videoapp.database.dataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.videoapp.database.entities.TopRatedMoviesEntity;
import java.util.List;

@Dao
public interface TopRatedMoviesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTopRatedMovies(List<TopRatedMoviesEntity> topRatedMoviesEntity);

    @Query("SELECT * FROM TOP_RATED_MOVIES")
    List<TopRatedMoviesEntity> getTopRatedMovies();

    @Query("DELETE FROM TOP_RATED_MOVIES")
    void deleteAllTopRatedMovies();
}
