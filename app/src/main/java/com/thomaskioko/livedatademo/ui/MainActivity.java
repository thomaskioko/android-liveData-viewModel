package com.thomaskioko.livedatademo.ui;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.ui.viewmodel.MainActivityViewModel;

public class MainActivity extends LifecycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list);
        ProgressBar progressBar = findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);

        ViewModelProviders.of(this)
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
}
