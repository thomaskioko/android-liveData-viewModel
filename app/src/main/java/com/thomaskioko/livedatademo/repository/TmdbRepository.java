package com.thomaskioko.livedatademo.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.thomaskioko.livedatademo.repository.api.MovieResult;
import com.thomaskioko.livedatademo.repository.api.TmdbService;
import com.thomaskioko.livedatademo.repository.model.ApiResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class helps manage communication from repository to ViewModels
 *
 * @author Thomas Kioko
 */

@Singleton
public class TmdbRepository {

    private TmdbService mTmdbService;

    @Inject
    TmdbRepository(TmdbService tmdbService) {
        mTmdbService = tmdbService;
    }

    public LiveData<ApiResponse> getPopularMovies() {
        MutableLiveData<ApiResponse> apiResponseMutableLiveData = new MutableLiveData<>();
        Call<MovieResult> movieResultCall = mTmdbService.getPopularMovies();
        movieResultCall.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                apiResponseMutableLiveData.setValue(new ApiResponse(response.code(), response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                apiResponseMutableLiveData.setValue(new ApiResponse(t));
            }
        });

        return apiResponseMutableLiveData;
    }
}
