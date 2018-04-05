package com.thomaskioko.livedatademo.view.ui.common;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.view.View;

import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.view.ui.MainActivity;
import com.thomaskioko.livedatademo.view.ui.fragment.DetailsTransition;
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
    public NavigationController(MainActivity mainActivity) {
        containerId = R.id.container;
        fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToMovieListFragment() {
        MovieListFragment fragment = new MovieListFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void navigateToMovieDetailFragment(View sharedImageView, int movieId) {
        MovieDetailFragment fragment = MovieDetailFragment.create(movieId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailsTransition());
        }
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addSharedElement(sharedImageView, ViewCompat.getTransitionName(sharedImageView))
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
