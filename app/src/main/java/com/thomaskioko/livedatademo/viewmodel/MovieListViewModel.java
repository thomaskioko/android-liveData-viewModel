package com.thomaskioko.livedatademo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.thomaskioko.livedatademo.repository.TmdbRepository;
import com.thomaskioko.livedatademo.repository.model.ApiResponse;

import javax.inject.Inject;

import timber.log.Timber;


public class MovieListViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> mApiResponseMediatorLiveData;


    @Inject
    MovieListViewModel(@NonNull TmdbRepository tmdbRepository) {

    }


    @VisibleForTesting
    public LiveData<ApiResponse> getPopularMovies() {
        if (mApiResponseMediatorLiveData == null) {
            mApiResponseMediatorLiveData = new MediatorLiveData<>();
            loadPopularMovies();
        }
        return mApiResponseMediatorLiveData;
    }


    /**
     * Invoke {@link com.thomaskioko.livedatademo.repository.api.TmdbService} to fetch
     * list of popular movies
     */
    private void loadPopularMovies() {

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.d("@onCleared called");
    }

}
