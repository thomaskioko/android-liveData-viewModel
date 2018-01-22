package com.thomaskioko.livedatademo.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thomaskioko.livedatademo.db.MovieDao;
import com.thomaskioko.livedatademo.db.TmdbDb;
import com.thomaskioko.livedatademo.repository.api.MovieResult;
import com.thomaskioko.livedatademo.repository.api.TmdbService;
import com.thomaskioko.livedatademo.repository.model.ApiResponse;
import com.thomaskioko.livedatademo.repository.model.Movie;
import com.thomaskioko.livedatademo.repository.util.AppExecutors;
import com.thomaskioko.livedatademo.repository.util.NetworkBoundResource;
import com.thomaskioko.livedatademo.utils.AbsentLiveData;
import com.thomaskioko.livedatademo.vo.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This class helps manage communication from repository to ViewModels
 */

@Singleton
public class TmdbRepository {

    private TmdbService mTmdbService;
    private TmdbDb mTmdbDb;
    private MovieDao mMovieDao;
    private final AppExecutors mAppExecutors;

    @Inject
    TmdbRepository(AppExecutors appExecutors, TmdbService tmdbService, TmdbDb tmdbDb, MovieDao movieDao) {
        mTmdbService = tmdbService;
        mAppExecutors = appExecutors;
        mTmdbDb = tmdbDb;
        mMovieDao = movieDao;
    }

    public LiveData<Resource<MovieResult>> getPopularMovies() {
        return new NetworkBoundResource<MovieResult, MovieResult>(mAppExecutors) {
            @Override
            protected void saveCallResult(@NonNull MovieResult item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable MovieResult data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<MovieResult> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieResult>> createCall() {
                return mTmdbService.getPopularMovies();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Movie>>> searchMovie(String query) {
        return new NetworkBoundResource<List<Movie>, MovieResult>(mAppExecutors) {
            @Override
            protected void saveCallResult(@NonNull MovieResult item) {
                mMovieDao.insertMovies(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return Transformations.switchMap(mMovieDao.search(query), searchData -> {
                    if (searchData == null) {
                        return AbsentLiveData.create();
                    } else {
                        return mMovieDao.loadOrdered(searchData.repoIds);
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieResult>> createCall() {
                return mTmdbService.searchMovies(query);
            }
        }.asLiveData();
    }
}
