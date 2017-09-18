package com.thomaskioko.livedatademo.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.thomaskioko.livedatademo.di.qualifires.ViewModelKey;
import com.thomaskioko.livedatademo.viewmodel.MainActivityViewModel;
import com.thomaskioko.livedatademo.viewmodel.ProjectViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * @author Thomas Kioko
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindMainActivityViewModel(MainActivityViewModel mainActivityViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ProjectViewModelFactory projectViewModelFactory);
}
