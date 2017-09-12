package com.thomaskioko.livedatademo.repository;

import com.thomaskioko.livedatademo.repository.api.MovieResult;
import com.thomaskioko.livedatademo.repository.api.TmdbService;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

/**
 * @author Thomas Kioko
 */

@Singleton
public class RepositoryManager {

    private TmdbService mTmdbService;

    @Inject
    RepositoryManager(TmdbService tmdbService) {
        mTmdbService = tmdbService;
    }

    public Call<MovieResult> getPopularMovies() {
        return mTmdbService.getPopularMovies();
    }
}
