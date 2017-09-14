package com.thomaskioko.livedatademo.repository.model;

import com.thomaskioko.livedatademo.repository.api.MovieResult;

/**
 * @author Thomas Kioko
 */

public class ApiResponse {

    private MovieResult movieResult;
    private Throwable error;

    public ApiResponse(MovieResult movieResult) {
        this.movieResult = movieResult;
        this.error = null;
    }

    public ApiResponse(Throwable error) {
        this.error = error;
        this.movieResult = null;
    }

    public MovieResult getMovieResult() {
        return movieResult;
    }

    public void setMovieResult(MovieResult movieResult) {
        this.movieResult = movieResult;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
