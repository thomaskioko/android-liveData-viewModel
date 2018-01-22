package com.thomaskioko.livedatademo.util;

import com.thomaskioko.livedatademo.repository.api.MovieResult;
import com.thomaskioko.livedatademo.repository.model.Movie;

import java.util.List;

/**
 * @author Thomas Kioko
 */

public class TestUtil {

    public static Movie createMovie(String posterPath) {
        Movie movie = new Movie();
        movie.setPosterUrl(posterPath);

        return movie;
    }

    public static MovieResult createMovieResult(Integer page, List<Movie> movieList) {
        MovieResult movieResult = new MovieResult();
        movieResult.setPage(page);
        movieResult.setResults(movieList);

        return movieResult;
    }
}
