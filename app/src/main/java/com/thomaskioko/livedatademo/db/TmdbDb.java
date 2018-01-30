package com.thomaskioko.livedatademo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.thomaskioko.livedatademo.repository.model.Movie;

/**
 * Main database description.
 */
@Database(entities = {Movie.class}, version = 1)
public abstract class TmdbDb extends RoomDatabase {

    abstract public MovieDao movieDao();
}
