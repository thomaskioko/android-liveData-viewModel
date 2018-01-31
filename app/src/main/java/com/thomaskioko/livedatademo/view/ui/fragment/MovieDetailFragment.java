package com.thomaskioko.livedatademo.view.ui.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.di.Injectable;

import butterknife.ButterKnife;

/**
 *
 */

public class MovieDetailFragment extends LifecycleFragment implements Injectable {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_detail_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
