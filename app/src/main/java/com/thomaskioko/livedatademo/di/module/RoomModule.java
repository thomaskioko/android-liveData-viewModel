package com.thomaskioko.livedatademo.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.thomaskioko.livedatademo.db.TmdbDb;
import com.thomaskioko.livedatademo.db.dao.GenreDao;
import com.thomaskioko.livedatademo.db.dao.MovieDao;
import com.thomaskioko.livedatademo.db.dao.VideoDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class RoomModule {

    @Singleton
    @Provides
    TmdbDb providesRoomDatabase(Application app) {
        return Room.databaseBuilder(app, TmdbDb.class, "tmdb_db").build();
    }

    @Singleton
    @Provides
    MovieDao provideMovieDao(TmdbDb db){
        return db.movieDao();
    }

    @Singleton
    @Provides
    GenreDao provideGenreDao(TmdbDb db){
        return db.genreDao();
    }


    @Singleton
    @Provides
    VideoDao VideoDao(TmdbDb db){
        return db.videoDao();
    }
}
