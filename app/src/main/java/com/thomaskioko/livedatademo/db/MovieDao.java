package com.thomaskioko.livedatademo.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.thomaskioko.livedatademo.repository.model.Movie;
import com.thomaskioko.livedatademo.vo.MovieSearchResult;

import java.util.List;

/**
 *
 */
@Dao
public abstract class MovieDao {

    @Query("SELECT * FROM Movie")
    public abstract LiveData<List<Movie>> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMovies(List<Movie> movieList);

    @Query("SELECT * FROM Movie WHERE query = :query")
    public abstract LiveData<Movie> search(String query);

    @Query("DELETE FROM Movie")
    public abstract void deleteAll();

}
