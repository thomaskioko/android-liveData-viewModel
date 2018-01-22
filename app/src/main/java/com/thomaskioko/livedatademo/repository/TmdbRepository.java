package com.thomaskioko.livedatademo.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thomaskioko.livedatademo.repository.api.MovieResult;
import com.thomaskioko.livedatademo.repository.api.TmdbService;
import com.thomaskioko.livedatademo.repository.model.ApiResponse;
import com.thomaskioko.livedatademo.repository.model.Movie;
import com.thomaskioko.livedatademo.repository.util.NetworkBoundResource;
import com.thomaskioko.livedatademo.repository.util.AppExecutors;
import com.thomaskioko.livedatademo.vo.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * This class helps manage communication from repository to ViewModels
 *
 * @author Thomas Kioko
 */

@Singleton
public class TmdbRepository {

    private TmdbService mTmdbService;
    private final AppExecutors mAppExecutors;

    @Inject
    TmdbRepository(AppExecutors appExecutors, TmdbService tmdbService) {
        mTmdbService = tmdbService;
        mAppExecutors = appExecutors;
    }

    public LiveData<Resource<MovieResult>> getPopularMovies() {
        return new NetworkBoundResource<MovieResult, MovieResult>(mAppExecutors){
            @Override
            protected void saveCallResult(@NonNull MovieResult item) {

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieResult>> createCall() {
                return mTmdbService.getPopularMovies();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Movie>>> searchMovie(String query) {
        return new NetworkBoundResource<List<Movie>, MovieResult>(mAppExecutors){
            @Override
            protected void saveCallResult(@NonNull MovieResult item) {
                Timber.d("@saveCallResult:: " + item.getResults());

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MovieResult>> createCall() {
                return mTmdbService.searchMovies(query);
            }
        }.asLiveData();
    }
}
