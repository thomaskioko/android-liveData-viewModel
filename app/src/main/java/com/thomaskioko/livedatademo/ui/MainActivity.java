package com.thomaskioko.livedatademo.ui;

import android.app.Activity;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.di.Injectable;
import com.thomaskioko.livedatademo.ui.viewmodel.MainActivityViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MainActivity extends LifecycleActivity implements Injectable, HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidInjection.inject(this);

        ListView listView = findViewById(R.id.list);
        ProgressBar progressBar = findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        ViewModelProviders.of(this, viewModelFactory)
                .get(MainActivityViewModel.class)
                .getGitUserNames()
                .observe(this, stringList -> {
                    // update UI
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, stringList);
                    // Assign adapter to ListView
                    listView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                });

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
