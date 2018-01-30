package com.thomaskioko.livedatademo.di.component;

import android.app.Application;

import com.thomaskioko.livedatademo.TmdbApp;
import com.thomaskioko.livedatademo.di.module.AppModule;
import com.thomaskioko.livedatademo.di.module.MainActivityModule;
import com.thomaskioko.livedatademo.di.module.RoomModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        MainActivityModule.class,
        RoomModule.class,
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(TmdbApp tmdbApp);
}
