package com.thomaskioko.livedatademo.di.module;

import com.thomaskioko.livedatademo.view.ui.fragment.MovieDetailFragment;
import com.thomaskioko.livedatademo.view.ui.fragment.MovieListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract MovieListFragment contributeMovieListFragment();

    @ContributesAndroidInjector
    abstract MovieDetailFragment contributeMovieDetailFragment();
}
