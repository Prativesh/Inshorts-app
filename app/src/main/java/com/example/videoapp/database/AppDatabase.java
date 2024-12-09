package com.example.videoapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.videoapp.database.dataAccessObject.NowPlayingMoviesDAO;
import com.example.videoapp.database.dataAccessObject.TopRatedMoviesDAO;
import com.example.videoapp.database.entities.NowPlayingMoviesEntity;
import com.example.videoapp.database.entities.TopRatedMoviesEntity;

@Database(entities = {TopRatedMoviesEntity.class, NowPlayingMoviesEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract TopRatedMoviesDAO topRatedMoviesDAO();
    public abstract NowPlayingMoviesDAO nowPlayingMoviesDAO();

    public static synchronized void initialize(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()  // Optional for handling schema migrations
                    .build();
        }
    }

    public static AppDatabase getInstance() {
        return instance;
    }
}
