package com.thomaskioko.livedatademo.view.ui.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.di.Injectable;
import com.thomaskioko.livedatademo.db.entity.Movie;
import com.thomaskioko.livedatademo.viewmodel.MovieDetailViewModel;
import com.thomaskioko.livedatademo.vo.Resource;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class MovieDetailFragment extends LifecycleFragment implements Injectable {

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.layout_movie_details)
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
    @BindView(R.id.layout_display_info)
    RelativeLayout layoutDisplayInfo;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.error_msg)
    TextView mErrorTextView;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MovieDetailViewModel.class);

        movieDetailViewModel.setMovieId(getArguments().getInt(BUNDLE_MOVIE_ID));
        movieDetailViewModel.getMovie().observe(this, this::handleResponse);
    }

    private void handleResponse(Resource<Movie> movieResult) {
        switch (movieResult.status) {
            case LOADING:
                layoutDisplayInfo.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                layoutDisplayInfo.setVisibility(View.VISIBLE);
                break;
            case SUCCESS:
                layoutDisplayInfo.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                displayMovieInfo(movieResult.data);
                break;
            case ERROR:
                layoutDisplayInfo.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mErrorTextView.setVisibility(View.VISIBLE);
                mErrorTextView.setText(getResources().getString(R.string.error_no_results));
                break;
            default:
                break;
        }
    }

    private void displayMovieInfo(Movie mMovieResult) {
        if (mMovieResult != null) {
            //Image path
            String imagePath = getString(R.string.tmdb_image_url) +
                    getString(R.string.image_size_780) + mMovieResult.posterUrl;

            //Backdrop Image URL
            String imagePathBackDrop = getString(R.string.tmdb_image_url) +
                    getString(R.string.image_size_780)
                    + mMovieResult.backdropPath;

            Glide.with(getActivity())
                    .load(imagePath)
                    .asBitmap()
                    .centerCrop()
                    .into(mThumbnail);

            float rating = mMovieResult.voteAverage.floatValue() * 10;
            float popularity = mMovieResult.popularity.intValue();

            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd")
                    .withLocale(Locale.getDefault());

            //Get the year from the release date.
            LocalDate date = formatter.parseLocalDate(mMovieResult.releaseYear);

            mMoviePlot.setText(mMovieResult.overview);
            mMovieYear.setText(String.valueOf(date.getYear()));
            mMovieRating.setText(String.valueOf(mMovieResult.voteAverage));
            mMoviePopularity.setText(String.valueOf(popularity));
            mMovieVote.setText(String.valueOf(mMovieResult.voteCount));
            mCircularProgressBar.setProgressWithAnimation(rating);

            appBarLayout.setTitle(mMovieResult.title);


            Glide.with(imageView.getContext())
                    .load(imagePathBackDrop)
                    .asBitmap()
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, final GlideAnimation glideAnimation) {
                            super.onResourceReady(bitmap, glideAnimation);
                            Palette.from(bitmap).generate(palette -> {

                                if (palette.getDarkVibrantSwatch() != null) {
                                    mRelativeLayout.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
                                    mCircularProgressBar.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());

                                } else if (palette.getMutedSwatch() != null) {
                                    mRelativeLayout.setBackgroundColor(palette.getMutedSwatch().getRgb());
                                    mCircularProgressBar.setBackgroundColor(palette.getMutedSwatch().getRgb());
                                }
                                if (palette.getLightVibrantSwatch() != null) {
                                    mCircularProgressBar.setColor(palette.getLightVibrantSwatch().getRgb());
                                } else if (palette.getLightMutedSwatch() != null) {
                                    mCircularProgressBar.setColor(palette.getLightMutedSwatch().getRgb());
                                }
                            });
                        }
                    });
        } else {
            mErrorTextView.setVisibility(View.VISIBLE);
            mErrorTextView.setText(getResources().getString(R.string.error_no_results));
        }
    }
}
