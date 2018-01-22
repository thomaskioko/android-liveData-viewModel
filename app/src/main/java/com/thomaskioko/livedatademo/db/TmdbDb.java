package com.thomaskioko.livedatademo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.thomaskioko.livedatademo.repository.model.Movie;
import com.thomaskioko.livedatademo.vo.MovieSearchResult;

/**
 * Main database description.
 */
@Database(entities = {Movie.class, MovieSearchResult.class}, version = 1)
public abstract class TmdbDb extends RoomDatabase {

    abstract public MovieDao movieDao();
}
