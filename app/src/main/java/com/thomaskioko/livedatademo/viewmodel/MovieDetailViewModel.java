package com.thomaskioko.livedatademo.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.thomaskioko.livedatademo.repository.TmdbRepository;

import javax.inject.Inject;


public class MovieDetailViewModel extends ViewModel {

    private TmdbRepository mTmdbRepository;

    @Inject
    MovieDetailViewModel(TmdbRepository tmdbRepository){
        mTmdbRepository = tmdbRepository;
    }
}
