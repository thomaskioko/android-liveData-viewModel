package com.thomaskioko.livedatademo;

import android.app.Application;

import timber.log.BuildConfig;
import timber.log.Timber;

/**
 * @author Thomas Kioko
 */

public class LiveDataApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }
}
