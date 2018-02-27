package com.thomaskioko.livedatademo.util;

import com.thomaskioko.livedatademo.repository.api.MovieResult;
import com.thomaskioko.livedatademo.db.entity.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Kioko
 */

public class TestUtil {

    public static Movie createMovie(String title, String posterPath) {
        String overView = "Set in a post-apocalyptic world, young Thomas is deposited in a community of boys after his memory is erased, soon learning they're all trapped in a maze that will require him to join forces with fellow “runners” for a shot at escape.";
        String backDropPath = "/lkOZcsXcOLZYeJ2YxJd3vSldvU4.jpg.jpg";
        return new Movie(198663, posterPath, 7.8, "2014-09-10", title,
                false, overView, "The Maze Runner", "en", backDropPath,
                732.263205, 6559, false, 7.3);
    }

    public static MovieResult createMovieResult(Integer page, List<Movie> movieList) {
        MovieResult movieResult = new MovieResult();
        movieResult.setPage(page);
        movieResult.setResults(movieList);

        return movieResult;
    }

    public static List<Movie> getMovieList() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(createMovie("The Maze Runner", "\\/coss7RgL0NH6g4fC2s5atvf3dFO.jpg"));
        movieList.add(createMovie("Jumanji", "\\/47pLZ1gr63WaciDfHCpmoiXJlVr.jpg"));

        return movieList;
    }
}
