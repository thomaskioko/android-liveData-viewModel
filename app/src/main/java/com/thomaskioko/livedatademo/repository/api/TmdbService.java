package com.thomaskioko.livedatademo.repository.api;

import android.arch.lifecycle.LiveData;

import com.thomaskioko.livedatademo.repository.model.ApiResponse;
import com.thomaskioko.livedatademo.db.entity.Movie;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface TmdbService {
    
    @GET("movie/top_rated?")
    LiveData<ApiResponse<MovieResult>> getTopRatedMovies();

    @GET("movie/popular?")
    LiveData<ApiResponse<MovieResult>> getPopularMovies();

    @GET("movie/latest")
    LiveData<ApiResponse<MovieResult>> getLatestMovies();

    @GET("discover/movie?sort_by=popularity.desc")
    LiveData<ApiResponse<MovieResult>> discoverPopularMovies();

    @GET("search/movie?")
    LiveData<ApiResponse<MovieResult>> searchMovies(@Query("query") String query);

    @GET("movie/{movie_id}")
    LiveData<ApiResponse<Movie>> getMovieById(@Path("movie_id") int movieId);

    @GET("movie/{movie_id}/similar")
    LiveData<ApiResponse<Movie>> getSimilarMovies(@Path("movie_id") int movieId);

    @GET("genre/movie/list")
    LiveData<ApiResponse<GenreResponse>> getGenres();

    @GET("movie/{movie_id}/videos")
    LiveData<ApiResponse<VideoResult>> getMovieVideos(@Path("movie_id") int movieId);
}
