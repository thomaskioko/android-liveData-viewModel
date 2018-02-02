package com.thomaskioko.livedatademo.view.ui.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.di.Injectable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class MovieDetailFragment extends LifecycleFragment implements Injectable {

    @BindView(R.id.layout_movie_title)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.movie_detail_year)
    TextView mMovieYear;
    @BindView(R.id.movie_detail_plot)
    TextView mMoviePlot;
    @BindView(R.id.movie_detail_thumbnail)
    ImageView mThumbnail;
    @BindView(R.id.movie_detail_rating)
    TextView mMovieRating;
    @BindView(R.id.movie_detail_popularity)
    TextView mMoviePopularity;
    @BindView(R.id.movie_detail_votes)
    TextView mMovieVote;
    @BindView(R.id.circularProgressBar)
    CircularProgressBar mCircularProgressBar;
    @BindView(R.id.coordinated_layout)
    CoordinatorLayout mCoordinatedLayout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout appBarLayout;
    @BindView(R.id.backdrop)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private static final String BUNDLE_MOVIE_ID = "MOVIE_ID";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_detail_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public static MovieDetailFragment create(int movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }
}
