package com.thomaskioko.livedatademo.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.thomaskioko.livedatademo.db.entity.Movie;

import java.util.List;

/**
 *  Interface for database access on Movie related operations.
 */
@Dao
public abstract class MovieDao {

    @Query("SELECT * FROM Movie")
    public abstract LiveData<List<Movie>> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Movie... movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMovies(List<Movie> movieList);

    @Query("DELETE FROM Movie")
    public abstract void deleteAll();

    @Query("SELECT * FROM Movie where title = :title")
    public abstract LiveData<List<Movie>> searchMovieByTitle(String title);

    @Query("SELECT * FROM Movie where id = :id")
    public abstract LiveData<Movie> searchMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long createMovieIfNotExists(Movie movie);
}
