package com.thomaskioko.livedatademo.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thomaskioko.livedatademo.db.TmdbDb;
import com.thomaskioko.livedatademo.db.dao.GenreDao;
import com.thomaskioko.livedatademo.db.dao.MovieDao;
import com.thomaskioko.livedatademo.db.entity.Genre;
import com.thomaskioko.livedatademo.db.entity.Movie;
import com.thomaskioko.livedatademo.repository.api.GenreResponse;
import com.thomaskioko.livedatademo.repository.api.MovieResult;
import com.thomaskioko.livedatademo.repository.api.TmdbService;
import com.thomaskioko.livedatademo.repository.model.ApiResponse;
import com.thomaskioko.livedatademo.repository.util.AppExecutors;
import com.thomaskioko.livedatademo.repository.util.NetworkBoundResource;
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
    private GenreDao mGenreDao;
    private final AppExecutors mAppExecutors;

    @Inject
    TmdbRepository(AppExecutors appExecutors, TmdbService tmdbService, TmdbDb tmdbDb, MovieDao movieDao,
                   GenreDao genreDao) {
        mTmdbService = tmdbService;
        mAppExecutors = appExecutors;
        mTmdbDb = tmdbDb;
        mMovieDao = movieDao;
        mGenreDao = genreDao;
    }

    public LiveData<Resource<List<Movie>>> getPopularMovies() {
        return new NetworkBoundResource<List<Movie>, MovieResult>(mAppExecutors) {
            @Override
            protected void saveCallResult(@NonNull MovieResult item) {
                mMovieDao.insertMovies(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb()  {
                return mMovieDao.findAll();
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
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                //Fetch from the db
                return mMovieDao.searchMovieByTitle(query);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieResult>> createCall() {
                return mTmdbService.searchMovies(query);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Movie>> getMovieById(int movieId) {
        return new NetworkBoundResource<Movie, Movie>(mAppExecutors) {

            @Override
            protected void saveCallResult(@NonNull Movie item) {
                mMovieDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable Movie data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<Movie> loadFromDb() {
                return mMovieDao.searchMovieById(movieId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Movie>> createCall() {
                return mTmdbService.getMovieById(movieId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Genre>>> getGenres() {
        return new NetworkBoundResource<List<Genre>, GenreResponse>(mAppExecutors) {
            @Override
            protected void saveCallResult(@NonNull GenreResponse item) {
                mGenreDao.insertGenres(item.getGenres());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Genre> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Genre>> loadFromDb() {
                return mGenreDao.findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<GenreResponse>> createCall() {
                return mTmdbService.getGenres();
            }
        }.asLiveData();
    }
}
