package com.thomaskioko.livedatademo.di.module;

import com.thomaskioko.livedatademo.repository.api.TmdbService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;


@Module(includes = {
        ViewModelModule.class,
        NetworkModule.class
})
public class AppModule {

    @Provides
    @Singleton
    TmdbService provideTmdbService(Retrofit retrofit) {
        return retrofit.create(TmdbService.class);
    }

}
