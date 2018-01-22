package com.thomaskioko.livedatademo.repository.api;

import android.arch.lifecycle.LiveData;

import com.thomaskioko.livedatademo.repository.model.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Thomas Kioko
 */

public interface TmdbService {

    /**
     * Get top rated movies
     *
     * @return JSON Result
     */
    @GET("movie/top_rated?")
    LiveData<ApiResponse<MovieResult>> getTopRatedMovies();

    /**
     * Get popular movies.
     *
     * @return JSON Result
     */
    @GET("movie/popular?")
    LiveData<ApiResponse<MovieResult>> getPopularMovies();

    @GET("search/movie?")
    LiveData<ApiResponse<MovieResult>> searchMovies(@Query("query") String query);
}
