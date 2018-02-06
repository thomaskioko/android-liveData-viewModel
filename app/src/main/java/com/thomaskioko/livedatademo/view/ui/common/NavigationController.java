package com.thomaskioko.livedatademo.view.ui.common;

import android.support.v4.app.FragmentManager;

import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.view.ui.MainActivity;
import com.thomaskioko.livedatademo.view.ui.fragment.MovieDetailFragment;
import com.thomaskioko.livedatademo.view.ui.fragment.MovieListFragment;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link MainActivity}.
 */

public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(MainActivity mainActivity){
        containerId = R.id.container;
        fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToMovieListFragment(){
        MovieListFragment fragment = new MovieListFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void navigateToMovieDetailFragment(int movieId){
        MovieDetailFragment fragment = MovieDetailFragment.create(movieId);
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
