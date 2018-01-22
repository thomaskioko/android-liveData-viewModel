package com.thomaskioko.livedatademo.repository.api;

import retrofit2.Call;
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
    Call<MovieResult> getTopRatedMovies();

    /**
     * Get popular movies.
     *
     * @return JSON Result
     */
    @GET("movie/popular?")
    Call<MovieResult> getPopularMovies();

    @GET("search/movie?")
    Call<MovieResult> searchMovies(@Query("query") String query);
}
