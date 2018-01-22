package com.thomaskioko.livedatademo;

import android.app.Application;

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 * See {@link com.thomaskioko.livedatademo.util.TmdbTestRunner}.
 */
public class TestApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
