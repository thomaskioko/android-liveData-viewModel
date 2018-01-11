package com.thomaskioko.livedatademo.di.module;

import com.thomaskioko.livedatademo.view.ui.fragment.MovieListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author Thomas Kioko
 */

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract MovieListFragment contributeMovieListFragment();
}
