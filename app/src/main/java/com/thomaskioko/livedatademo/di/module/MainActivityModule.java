package com.thomaskioko.livedatademo.di.module;

import com.thomaskioko.livedatademo.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author Thomas Kioko
 */

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

}
