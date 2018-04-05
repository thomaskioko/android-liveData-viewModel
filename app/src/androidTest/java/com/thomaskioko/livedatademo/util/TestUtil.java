package com.thomaskioko.livedatademo.util;

import com.thomaskioko.livedatademo.db.entity.Movie;
import com.thomaskioko.livedatademo.db.entity.TmdbVideo;

import java.util.ArrayList;
import java.util.List;


public class TestUtil {

    public static Movie createMovie(String title, String posterPath) {
        String overView = "Set in a post-apocalyptic world, young Thomas is deposited in a community of boys after his memory is erased, soon learning they're all trapped in a maze that will require him to join forces with fellow “runners” for a shot at escape.";
        String backDropPath = "/lkOZcsXcOLZYeJ2YxJd3vSldvU4.jpg.jpg";
        List<Integer> genreIds = new ArrayList<>();
        genreIds.add(23);
        return new Movie(198663, posterPath, 7.8, "2014-09-10", title,
                false, overView, "The Maze Runner", "en", backDropPath,
                732.263205, 6559, false, 7.3, genreIds);
    }


    public static List<Movie> getMovieList() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(createMovie("The Maze Runner", "\\/coss7RgL0NH6g4fC2s5atvf3dFO.jpg"));
        movieList.add(createMovie("Jumanji", "\\/47pLZ1gr63WaciDfHCpmoiXJlVr.jpg"));

        return movieList;
    }


    public static TmdbVideo createTmdbVideo() {
        return new TmdbVideo("571bf094c3a368525f006b86", "Official Trailer", "Trailer",
                "XRVD32rnzOw", 1080, "YouTube", "en", "US", 198663
        );
    }


    public static List<TmdbVideo> getTmdbVideoList() {
        List<TmdbVideo> tmdbVideos = new ArrayList<>();
        tmdbVideos.add(createTmdbVideo());
        tmdbVideos.add(createTmdbVideo());
        tmdbVideos.add(createTmdbVideo());

        return tmdbVideos;
    }
}
