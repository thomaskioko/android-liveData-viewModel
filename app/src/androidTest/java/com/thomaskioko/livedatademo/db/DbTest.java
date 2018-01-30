package com.thomaskioko.livedatademo.db;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;

/**
 *
 */

abstract public class DbTest {

    protected TmdbDb tmdbDb;

    @Before
    public void initDb() {
        tmdbDb = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                TmdbDb.class
        ).build();
    }

    @After
    public void close(){
        tmdbDb.close();
    }
}
