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

/**
 * @author Thomas Kioko
 */

public class MovieListViewModel extends ViewModel {

    private MediatorLiveData<ApiResponse> mApiResponseMediatorLiveData;
    private MediatorLiveData<ApiResponse> mSearchMediatorLiveData;
    private TmdbRepository tmdbRepository;

    @Inject
    MovieListViewModel(@NonNull TmdbRepository tmdbRepository) {
        this.tmdbRepository = tmdbRepository;
    }

    @VisibleForTesting
    public LiveData<ApiResponse> getPopularMovies() {
        if (mApiResponseMediatorLiveData == null) {
            mApiResponseMediatorLiveData = new MediatorLiveData<>();
            loadPopularMovies();
        }
        return mApiResponseMediatorLiveData;
    }

    @VisibleForTesting
    public LiveData<ApiResponse> getSearchMovie(String query) {
        if (mSearchMediatorLiveData == null) {
            mSearchMediatorLiveData = new MediatorLiveData<>();
            searchMovie(query);
        }
        return mSearchMediatorLiveData;
    }

    /**
     * Invoke {@link com.thomaskioko.livedatademo.repository.api.TmdbService} to fetch
     * list of popular movies
     */
    private void loadPopularMovies() {
        mApiResponseMediatorLiveData.addSource(
                tmdbRepository.getPopularMovies(),
                apiResponse -> mApiResponseMediatorLiveData.postValue(apiResponse)
        );
    }

    private void searchMovie(String query) {
        mSearchMediatorLiveData.addSource(
                tmdbRepository.searchMovie(query),
                apiResponse -> mSearchMediatorLiveData.postValue(apiResponse)
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.d("@onCleared called");
    }

}
