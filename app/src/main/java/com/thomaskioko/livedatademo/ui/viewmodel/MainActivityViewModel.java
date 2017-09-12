package com.thomaskioko.livedatademo.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.thomaskioko.livedatademo.repository.RepositoryManager;
import com.thomaskioko.livedatademo.repository.api.MovieResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * @author Thomas Kioko
 */

public class MainActivityViewModel extends ViewModel {

    RepositoryManager mRepositoryManager;
    private MutableLiveData<List<String>> mListMutableLiveData;

    @Inject
    MainActivityViewModel(@NonNull RepositoryManager repositoryManager) {
        mRepositoryManager = repositoryManager;
    }


    /**
     * Helper method that returns a list of user names.
     *
     * @return {@link MutableLiveData} list of users
     */
    public LiveData<List<String>> getGitUserNames() {
        if (mListMutableLiveData == null) {
            mListMutableLiveData = new MutableLiveData<>();
            loadUserNames();
        }
        return mListMutableLiveData;
    }

    /**
     * Helper method to simulate loading of data from a server. We will replace this later.
     */
    private void loadUserNames() {
        Handler myHandler = new Handler();
        myHandler.postDelayed(() -> {
            List<String> fruitsStringList = new ArrayList<>();
            fruitsStringList.add("@code_wizard");
            fruitsStringList.add("@ninja_developer");
            fruitsStringList.add("@denzel");
            fruitsStringList.add("@bananaPeel");
            fruitsStringList.add("@kioko");
            long seed = System.nanoTime();
            Collections.shuffle(fruitsStringList, new Random(seed));

            mListMutableLiveData.setValue(fruitsStringList);
        }, 5000);

    }

    /**
     * Invoke {@link com.thomaskioko.livedatademo.repository.api.TmdbService} to fetch
     * list of popular movies
     */
    public void getPopularMovies() {
        mRepositoryManager.getPopularMovies().enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                Timber.i("@getPopularMovies:: Status Code " + response.code());
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                Timber.e("@getPopularMovies:: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.d("@onCleared called");
    }

}
