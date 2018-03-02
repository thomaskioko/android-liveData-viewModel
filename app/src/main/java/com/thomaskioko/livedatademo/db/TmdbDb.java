package com.thomaskioko.livedatademo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.thomaskioko.livedatademo.db.dao.GenreDao;
import com.thomaskioko.livedatademo.db.dao.MovieDao;
import com.thomaskioko.livedatademo.db.dao.VideoDao;
import com.thomaskioko.livedatademo.db.entity.Genre;
import com.thomaskioko.livedatademo.db.entity.Movie;
import com.thomaskioko.livedatademo.db.entity.TmdbVideo;

/**
 * Main database description.
 */
@Database(entities = {Movie.class, Genre.class, TmdbVideo.class}, version = 1)
public abstract class TmdbDb extends RoomDatabase {

    abstract public MovieDao movieDao();

    abstract public GenreDao genreDao();

    abstract public VideoDao videoDao();
}
