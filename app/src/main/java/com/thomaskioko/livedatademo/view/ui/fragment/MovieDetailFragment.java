package com.thomaskioko.livedatademo.view.ui.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.db.entity.Genre;
import com.thomaskioko.livedatademo.db.entity.Movie;
import com.thomaskioko.livedatademo.db.entity.TmdbVideo;
import com.thomaskioko.livedatademo.di.Injectable;
import com.thomaskioko.livedatademo.utils.DeviceUtils;
import com.thomaskioko.livedatademo.utils.TagView;
import com.thomaskioko.livedatademo.view.adapter.VideoListAdapter;
import com.thomaskioko.livedatademo.viewmodel.MovieDetailViewModel;
import com.thomaskioko.livedatademo.vo.Resource;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class MovieDetailFragment extends Fragment implements Injectable {

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.layout_movie_details)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.movie_detail_year)
    TextView mMovieYear;
    @BindView(R.id.movie_detail_plot)
    TextView mMoviePlot;
    @BindView(R.id.iv_poster_profile)
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
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.backdrop)
    ImageView imageViewBackDrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.layout_display_info)
    RelativeLayout layoutDisplayInfo;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.error_msg)
    TextView mErrorTextView;
    @BindView(R.id.color_bar_info_wrapper)
    View colorView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.rating_text)
    TextView tvRating;
    @BindView(R.id.recyclerview_trailer)
    RecyclerView mRecyclerViewTrailer;
    @BindView(R.id.card_view_trailer)
    CardView mCardViewTrailer;
    @BindView(R.id.fabTrailer)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.tags_view)
    TagView tagView;


    private MovieDetailViewModel movieDetailViewModel;
    private VideoListAdapter mVideoListAdapter;
    private List<String> genres = new ArrayList<>();
    private static final String BUNDLE_MOVIE_ID = "MOVIE_ID";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Postpone the shared element enter transition.
        postponeEnterTransition();
    }

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

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        DeviceUtils.setTranslucentStatusBar(getActivity().getWindow(), android.R.color.transparent);

        mToolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        mVideoListAdapter = new VideoListAdapter((sharedImageView, video) -> {
            //TODO:: Open Youtube App

        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewTrailer.setLayoutManager(layoutManager);
        mRecyclerViewTrailer.setAdapter(mVideoListAdapter);


        movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MovieDetailViewModel.class);

        movieDetailViewModel.setMovieId(getArguments().getInt(BUNDLE_MOVIE_ID));
        movieDetailViewModel.getMovie().observe(this, this::handleResponse);
        movieDetailViewModel.getVideoMovies().observe(this, this::handleVideoResponse);
        movieDetailViewModel.getPalette().observe(this, this::handlePaletteResponse);
    }

    private void handlePaletteResponse(Palette palette) {

        for (String genreName : genres) {

            TagView.Tag tag = new TagView.Tag(genreName);
            tag.tagTextColor = Color.parseColor("#FFFFFF");
            tag.radius = 20f;
            tag.tagTextSize = 14f;
            tag.layoutBorderSize = 1f;
            tag.layoutBorderColor = Color.parseColor("#FFFFFF");
            if (palette.getDarkVibrantSwatch() != null) {
                tag.layoutColor = palette.getDarkVibrantSwatch().getRgb();
            } else if (palette.getMutedSwatch() != null) {
                tag.layoutColor = palette.getMutedSwatch().getRgb();
            }
            tagView.addTag(tag);
        }
    }

    private void handleGenreResponse(Resource<Genre> genreResource) {

        if (genreResource != null) {
            switch (genreResource.status) {
                case LOADING:
                    mProgressBar.setVisibility(View.VISIBLE);
                    layoutDisplayInfo.setVisibility(View.VISIBLE);
                    mErrorTextView.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    if (genreResource.data != null) {
                        mProgressBar.setVisibility(View.GONE);
                        mErrorTextView.setVisibility(View.GONE);
                        genres.add(genreResource.data.name);
                    }
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
    }


    private void handleVideoResponse(Resource<List<TmdbVideo>> listResource) {
        if (listResource != null) {
            switch (listResource.status) {
                case LOADING:
                    mProgressBar.setVisibility(View.VISIBLE);
                    layoutDisplayInfo.setVisibility(View.VISIBLE);
                    mErrorTextView.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    if (listResource.data != null && listResource.data.size() > 0) {
                        mProgressBar.setVisibility(View.GONE);
                        mErrorTextView.setVisibility(View.GONE);
                        mCardViewTrailer.setVisibility(View.VISIBLE);
                        mVideoListAdapter.setData(listResource.data);
                        mVideoListAdapter.notifyDataSetChanged();
                    }
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
    }

    private void handleResponse(Resource<Movie> movieResult) {
        switch (movieResult.status) {
            case LOADING:
                layoutDisplayInfo.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                layoutDisplayInfo.setVisibility(View.VISIBLE);
                mErrorTextView.setVisibility(View.GONE);
                break;
            case SUCCESS:
                layoutDisplayInfo.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                mErrorTextView.setVisibility(View.GONE);
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

            for (int genreId : mMovieResult.genreIds) {
                movieDetailViewModel.getMovieGenresById(genreId).observe(this, this::handleGenreResponse);
            }

            Glide.with(getActivity())
                    .load(imagePath)
                    .asBitmap()
                    .centerCrop()
                    .into(mThumbnail);

            float rating = (float) (mMovieResult.voteAverage / 2);
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
            title.setText(mMovieResult.title);
            ratingBar.setRating(rating);

            String movieRating = NumberFormat.getInstance(Locale.getDefault()).format(mMovieResult.voteCount);
            tvRating.setText(getString(R.string.place_holder_rating, movieRating));

            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isShow = true;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();

                    }
                    if (scrollRange + verticalOffset == 0) {
                        isShow = true;
                        collapsingToolbarLayout.setTitle(mMovieResult.title);
                    } else if (isShow) {
                        collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                        isShow = false;
                    }
                }
            });

            Glide.with(getActivity())
                    .load(imagePathBackDrop)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            startPostponedEnterTransition();
                            startPostponedEnterTransition();
                            Palette.from(resource).generate(palette -> {
                                movieDetailViewModel.setPalette(palette);
                                updatePaletteColorViews(palette);
                            });
                            return false;
                        }
                    })
                    .into(imageViewBackDrop);
        } else {
            mErrorTextView.setVisibility(View.VISIBLE);
            mErrorTextView.setText(getResources().getString(R.string.error_no_results));
        }
    }

    private void updatePaletteColorViews(Palette palette) {
        if (palette.getDarkVibrantSwatch() != null) {
            mRelativeLayout.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
            mCircularProgressBar.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
            colorView.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
            collapsingToolbarLayout.setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
            collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkVibrantSwatch().getRgb());
            collapsingToolbarLayout.setContentScrimColor(palette.getDarkVibrantSwatch().getRgb());

        } else if (palette.getMutedSwatch() != null) {
            mRelativeLayout.setBackgroundColor(palette.getMutedSwatch().getRgb());
            mCircularProgressBar.setBackgroundColor(palette.getMutedSwatch().getRgb());
            colorView.setBackgroundColor(palette.getMutedSwatch().getRgb());
            collapsingToolbarLayout.setBackgroundColor(palette.getMutedSwatch().getRgb());
            collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedSwatch().getRgb());
            collapsingToolbarLayout.setContentScrimColor(palette.getMutedSwatch().getRgb());
        }

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();

        if (palette.getLightVibrantSwatch() != null) {
            mCircularProgressBar.setColor(palette.getLightVibrantSwatch().getRgb());
            stars.getDrawable(2).setColorFilter(palette.getLightVibrantSwatch().getRgb(), PorterDuff.Mode.SRC_ATOP);
        } else if (palette.getLightMutedSwatch() != null) {
            mCircularProgressBar.setColor(palette.getLightMutedSwatch().getRgb());
            stars.getDrawable(2).setColorFilter(palette.getLightMutedSwatch().getRgb(), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
