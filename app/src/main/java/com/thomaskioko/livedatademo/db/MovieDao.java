package com.thomaskioko.livedatademo.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.util.SparseIntArray;

import com.thomaskioko.livedatademo.repository.model.Movie;
import com.thomaskioko.livedatademo.vo.MovieSearchResult;

import java.util.Collections;
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

    @Query("SELECT * FROM MovieSearchResult WHERE query = :query")
    public abstract LiveData<MovieSearchResult> search(String query);

    public LiveData<List<Movie>> loadOrdered(List<Integer> repoIds) {
        SparseIntArray order = new SparseIntArray();
        int index = 0;
        for (Integer repoId : repoIds) {
            order.put(repoId, index++);
        }
        return Transformations.map(loadById(repoIds), repositories -> {
            Collections.sort(repositories, (r1, r2) -> {
                int pos1 = order.get(r1.id);
                int pos2 = order.get(r2.id);
                return pos1 - pos2;
            });
            return repositories;
        });
    }

    @Query("SELECT * FROM Movie WHERE id in (:repoIds)")
    protected abstract LiveData<List<Movie>> loadById(List<Integer> repoIds);

}
