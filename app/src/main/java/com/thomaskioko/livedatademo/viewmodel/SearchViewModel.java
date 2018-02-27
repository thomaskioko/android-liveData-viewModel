package com.thomaskioko.livedatademo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.thomaskioko.livedatademo.repository.TmdbRepository;
import com.thomaskioko.livedatademo.db.entity.Movie;
import com.thomaskioko.livedatademo.vo.Resource;
import com.thomaskioko.livedatademo.utils.AbsentLiveData;
import com.thomaskioko.livedatademo.utils.Objects;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import timber.log.Timber;


public class SearchViewModel extends ViewModel {

    private final MutableLiveData<String> query = new MutableLiveData<>();
    private final LiveData<Resource<List<Movie>>> searchResults;

    @Inject
    SearchViewModel(@NonNull TmdbRepository tmdbRepository) {
        searchResults = Transformations.switchMap(query, search -> {
            
            if (search == null || search.trim().length() == 0) {
                return AbsentLiveData.create();
            } else {
                return tmdbRepository.searchMovie(search);
            }
        });
    }

    @VisibleForTesting
    public LiveData<Resource<List<Movie>>> getSearchResults() {
        return searchResults;
    }

    public void setSearchQuery(@NonNull String originalInput) {
        String input = originalInput.toLowerCase(Locale.getDefault()).trim();
        if (Objects.equals(input, query.getValue())) {
            return;
        }
        query.setValue(input);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.d("@onCleared called");
    }

}
