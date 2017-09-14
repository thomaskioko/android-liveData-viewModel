package com.thomaskioko.livedatademo.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.thomaskioko.livedatademo.repository.TmdbRepository;
import com.thomaskioko.livedatademo.repository.api.ApiResponse;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * @author Thomas Kioko
 */

public class MainActivityViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> mApiResponseMediatorLiveData;
    private TmdbRepository tmdbRepository;

    @Inject
    MainActivityViewModel(@NonNull TmdbRepository tmdbRepository) {
        this.tmdbRepository = tmdbRepository;
    }

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
        mApiResponseMediatorLiveData.addSource(
                tmdbRepository.getPopularMovies(),
                apiResponse -> mApiResponseMediatorLiveData.setValue(apiResponse)
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.d("@onCleared called");
    }

}
