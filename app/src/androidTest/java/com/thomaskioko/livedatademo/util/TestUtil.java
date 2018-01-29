package com.thomaskioko.livedatademo.util;

import com.thomaskioko.livedatademo.repository.api.MovieResult;
import com.thomaskioko.livedatademo.repository.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Kioko
 */

public class TestUtil {

    public static Movie createMovie(String title, String posterPath) {
        return new Movie(346364, posterPath, 7.8, 2017, title);
    }

    public static MovieResult createMovieResult(Integer page, List<Movie> movieList) {
        MovieResult movieResult = new MovieResult();
        movieResult.setPage(page);
        movieResult.setResults(movieList);

        return movieResult;
    }

    public static List<Movie> getMovieList(){
        List<Movie> movieList = new ArrayList<>();
        movieList.add(createMovie("Star Wars: The Last Jedi", "\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg"));
        movieList.add(createMovie("Star Wars: The Last Jedi", "\\/47pLZ1gr63WaciDfHCpmoiXJlVr.jpg"));

        return  movieList;
    }
}
