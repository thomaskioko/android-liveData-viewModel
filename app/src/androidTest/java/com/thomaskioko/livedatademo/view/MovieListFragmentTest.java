package com.thomaskioko.livedatademo.view;

import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.repository.api.MovieResult;
import com.thomaskioko.livedatademo.repository.model.ApiResponse;
import com.thomaskioko.livedatademo.repository.model.Movie;
import com.thomaskioko.livedatademo.testing.SingleFragmentActivity;
import com.thomaskioko.livedatademo.util.EspressoTestUtil;
import com.thomaskioko.livedatademo.util.RecyclerViewMatcher;
import com.thomaskioko.livedatademo.util.TestUtil;
import com.thomaskioko.livedatademo.util.ViewModelUtil;
import com.thomaskioko.livedatademo.view.ui.fragment.MovieListFragment;
import com.thomaskioko.livedatademo.viewmodel.MovieListViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Thomas Kioko
 */

@RunWith(AndroidJUnit4.class)
public class MovieListFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityTestRule =
            new ActivityTestRule<>(SingleFragmentActivity.class, true, true);

    private MovieListViewModel viewModel;
    private MediatorLiveData<ApiResponse> mApiResponseMediatorLiveData = new MediatorLiveData<>();

    @Before
    public void init() {
        EspressoTestUtil.disableProgressBarAnimations(activityTestRule);
        MovieListFragment movieListFragment = new MovieListFragment();

        viewModel = mock(MovieListViewModel.class);
        when(viewModel.getPopularMovies()).thenReturn(mApiResponseMediatorLiveData);

        movieListFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);

        activityTestRule.getActivity().setFragment(movieListFragment);
    }

    @Test
    public void loadResults() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(TestUtil.createMovie("\\/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg"));
        movieList.add(TestUtil.createMovie("\\/47pLZ1gr63WaciDfHCpmoiXJlVr.jpg"));

        MovieResult movieResult = TestUtil.createMovieResult(1, movieList);
        mApiResponseMediatorLiveData.postValue(new ApiResponse(200, movieResult));

        onView(listMatcher().atPosition(0)).check(matches(isDisplayed()));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void error() {
        mApiResponseMediatorLiveData.postValue(new ApiResponse(new Throwable("Could not fetch movies!")));
        onView(withId(R.id.tvError)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @NonNull
    private RecyclerViewMatcher listMatcher() {
        return new RecyclerViewMatcher(R.id.recyclerView);
    }

}
