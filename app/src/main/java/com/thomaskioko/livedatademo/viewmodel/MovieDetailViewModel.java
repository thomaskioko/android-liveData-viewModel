package com.thomaskioko.livedatademo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.thomaskioko.livedatademo.repository.TmdbRepository;
import com.thomaskioko.livedatademo.repository.model.Movie;
import com.thomaskioko.livedatademo.utils.AbsentLiveData;
import com.thomaskioko.livedatademo.utils.Objects;
import com.thomaskioko.livedatademo.vo.Resource;

import javax.inject.Inject;


public class MovieDetailViewModel extends ViewModel {

    @VisibleForTesting
    final MutableLiveData<Integer> movieId = new MutableLiveData<>();
    private TmdbRepository mTmdbRepository;
    private final LiveData<Resource<Movie>> movie;

    @Inject
    MovieDetailViewModel(TmdbRepository tmdbRepository) {
        mTmdbRepository = tmdbRepository;
        movie = Transformations.switchMap(movieId, movieId -> {
            if (movieId == null) {
                return AbsentLiveData.create();
            } else {
                return mTmdbRepository.getMovieById(movieId);
            }
        });
    }

    @VisibleForTesting
    public LiveData<Resource<Movie>> getMovie() {
        return movie;
    }

    @VisibleForTesting
    public void setMovieId(int movieId) {
        if (Objects.equals(this.movieId.getValue(), movieId)) {
            return;
        }

        this.movieId.setValue(movieId);
    }
}
