package com.thomaskioko.livedatademo.repository.api;

import android.arch.lifecycle.LiveData;

import com.thomaskioko.livedatademo.repository.model.ApiResponse;
import com.thomaskioko.livedatademo.db.entity.Movie;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


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

    /**
     * Get Movie by ID.
     *
     * @return JSON Result
     */
    @GET("movie/{movie_id}")
    LiveData<ApiResponse<Movie>> getMovieById(@Path("movie_id") int movieId);
}
