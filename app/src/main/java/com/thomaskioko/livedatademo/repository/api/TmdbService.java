package com.thomaskioko.livedatademo.repository.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Thomas Kioko
 */

public interface TmdbService {

    /**
     * Get top rated movies
     *
     * @return JSON Result
     */
    @GET("top_rated?")
    Call<MovieResult> getTopRatedMovies();

    /**
     * Get popular movies.
     *
     * @return JSON Result
     */
    @GET("popular?")
    Call<MovieResult> getPopularMovies();
}
