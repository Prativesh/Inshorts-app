package com.example.videoapp.database.dataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.videoapp.database.entities.NowPlayingMoviesEntity;
import java.util.List;

@Dao
public interface NowPlayingMoviesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNowPlayingMovies(List<NowPlayingMoviesEntity> nowPlayingMoviesEntity);

    @Query("SELECT * FROM NOW_PLAYING_MOVIES")
    List<NowPlayingMoviesEntity> getNowPlayingMovies();

    @Query("DELETE FROM NOW_PLAYING_MOVIES")
    void deleteAllNowPlayingMovies();
}
