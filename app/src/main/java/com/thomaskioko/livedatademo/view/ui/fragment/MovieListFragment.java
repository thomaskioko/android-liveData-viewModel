package com.thomaskioko.livedatademo.view.ui.fragment;

import android.app.SearchManager;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.di.Injectable;
import com.thomaskioko.livedatademo.repository.model.Movie;
import com.thomaskioko.livedatademo.view.adapter.MovieListAdapter;
import com.thomaskioko.livedatademo.viewmodel.MovieListViewModel;
import com.thomaskioko.livedatademo.vo.Resource;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.SEARCH_SERVICE;


public class MovieListFragment extends LifecycleFragment implements Injectable {

    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.tvError)
    TextView errorTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private MovieListViewModel mMovieListViewModel;
    private MovieListAdapter mMovieListAdapter;

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
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mRecyclerView.getContext(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMovieListAdapter = new MovieListAdapter();
        mRecyclerView.setAdapter(mMovieListAdapter);


       mMovieListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MovieListViewModel.class);

       mMovieListViewModel.getPopularMovies()
                .observe(this, this::handleResponse);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        // Retrieve the SearchView and plug it into SearchManager
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findMovie(query);
                dismissKeyboard(searchView.getWindowToken());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            searchView.onActionViewCollapsed();
            return false;
        });

    }

    private void findMovie(String query) {
        if (!query.isEmpty() || !query.equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            mMovieListViewModel.setSearchQuery(query);
            mMovieListViewModel.getSearchResults().observe(this, this::handleResponse);
        }

    }

    private void handleResponse(Resource<List<Movie>> listResource) {
        if (listResource != null) {
            switch (listResource.status) {
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    errorTextView.setText(listResource.message);
                    break;
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if (listResource.data != null && listResource.data.size() > 0) {
                        mMovieListAdapter.setData(listResource.data);
                        mMovieListAdapter.notifyDataSetChanged();
                    } else {
                        errorTextView.setText(getResources().getString(R.string.error_no_results));
                        errorTextView.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    progressBar.setVisibility(View.GONE);
                    errorTextView.setText(getResources().getString(R.string.error_no_results));
                    break;
            }
        }
    }

    private void dismissKeyboard(IBinder windowToken) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
    }

}
