package com.thomaskioko.livedatademo.view.ui.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.di.Injectable;
import com.thomaskioko.livedatademo.repository.api.MovieResult;
import com.thomaskioko.livedatademo.repository.model.ApiResponse;
import com.thomaskioko.livedatademo.repository.model.Movie;
import com.thomaskioko.livedatademo.view.adapter.MovieListAdapter;
import com.thomaskioko.livedatademo.viewmodel.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * @author Thomas Kioko
 */

public class MovieListFragment extends LifecycleFragment implements Injectable {

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.tvError)
    TextView errorTextView;

    MovieListAdapter mMovieListAdapter;
    private List<Movie> mMovieList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_list_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(mRecyclerView.getContext(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMovieListAdapter = new MovieListAdapter(mMovieList);
        mRecyclerView.setAdapter(mMovieListAdapter);

        progressBar.setVisibility(View.VISIBLE);
        ViewModelProviders.of(this, viewModelFactory)
                .get(MovieListViewModel.class)
                .getPopularMovies()
                .observe(this, this::handleApiResponse);
    }

    /**
     * Helper method that handles responses from, the API.It's responsible for displaying either
     * an error message of a list of movies based on the reponse from the server.
     *
     * @param apiResponse {@link ApiResponse}
     */
    private void handleApiResponse(ApiResponse apiResponse) {
        progressBar.setVisibility(View.GONE);

        if (apiResponse.getStatusCode() != 200) {
            Timber.e("API Error: ");
            errorTextView.setText(getResources().getString(R.string.error_loading));
        } else if (apiResponse.getError() != null) {
            Timber.e("Error: %s", apiResponse.getError().getMessage());
            errorTextView.setText(apiResponse.getError().getMessage());
            errorTextView.setVisibility(View.VISIBLE);
        } else {
            MovieResult movieResult = apiResponse.getMovieResult();

            mMovieList.addAll(movieResult.getResults());
            mMovieListAdapter.notifyDataSetChanged();
        }

    }

}
