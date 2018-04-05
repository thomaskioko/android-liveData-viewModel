package com.thomaskioko.livedatademo.db;

import com.thomaskioko.livedatademo.db.entity.Movie;
import com.thomaskioko.livedatademo.util.TestUtil;

import org.junit.Test;

import java.util.List;

import static com.thomaskioko.livedatademo.util.LiveDataTestUtil.getValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 *
 */

public class MovieDaoTest extends DbTest {

    @Test
    public void testInsertAndRead() throws InterruptedException {

        Movie movie = TestUtil.createMovie("Star Wars: The Last Jedi", "\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg");
        tmdbDb.movieDao().insert(movie);

        List<Movie> loadedMovie = getValue(tmdbDb.movieDao().searchMovieByTitle("Star Wars: The Last Jedi"));
        assertThat(loadedMovie, notNullValue());
        assertThat(loadedMovie.get(0).title, is("Star Wars: The Last Jedi"));
    }

    @Test
    public void testCreateIfNotExists_exists() throws InterruptedException {
        Movie movie = TestUtil.createMovie("Star Wars: The Last Jedi", "\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg");
        tmdbDb.movieDao().insert(movie);
        assertThat(tmdbDb.movieDao().createMovieIfNotExists(movie), is( -1L));
    }

    @Test
    public void testCreateIfNotExists_doesNotExists() throws InterruptedException {
        Movie movie = TestUtil.createMovie("Star Wars: The Last Jedi", "\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg");
        assertThat(tmdbDb.movieDao().createMovieIfNotExists(movie), is( 1L));
    }
}
