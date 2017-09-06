package com.thomaskioko.livedatademo.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import timber.log.Timber;

/**
 * @author Thomas Kioko
 */

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<String>> mListMutableLiveData;

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

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.d("@onCleared called");
    }

}
