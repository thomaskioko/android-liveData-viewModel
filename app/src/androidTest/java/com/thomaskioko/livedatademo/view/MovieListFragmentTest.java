package com.thomaskioko.livedatademo.view;

import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.widget.EditText;

import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.repository.model.Movie;
import com.thomaskioko.livedatademo.testing.SingleFragmentActivity;
import com.thomaskioko.livedatademo.util.EspressoTestUtil;
import com.thomaskioko.livedatademo.util.RecyclerViewMatcher;
import com.thomaskioko.livedatademo.util.TestUtil;
import com.thomaskioko.livedatademo.util.ViewModelUtil;
import com.thomaskioko.livedatademo.view.ui.fragment.MovieListFragment;
import com.thomaskioko.livedatademo.viewmodel.MovieListViewModel;
import com.thomaskioko.livedatademo.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
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
    private MediatorLiveData<Resource<List<Movie>>> result = new MediatorLiveData<>();

    @Before
    public void init() {
        EspressoTestUtil.disableProgressBarAnimations(activityTestRule);
        MovieListFragment movieListFragment = new MovieListFragment();

        viewModel = mock(MovieListViewModel.class);
        when(viewModel.getPopularMovies()).thenReturn(result);
        when(viewModel.getSearchResults()).thenReturn(result);

        movieListFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);

        activityTestRule.getActivity().setFragment(movieListFragment);
    }

    @Test
    public void testSearchMovie(){
        //click on the search icon
        onView(withId(R.id.action_search)).perform(click());

        //Type the test in the search  field and submit the query
        onView(isAssignableFrom(EditText.class)).perform(typeText("Spiderman"),
                pressKey(KeyEvent.KEYCODE_ENTER));

//        verify(viewModel).setSearchQuery("Spiderman");
        result.postValue(Resource.loading(null));

        //Check the progressBar is displayed
        onView(withId(R.id.progressbar)).check(matches(isDisplayed()));

    }


    @Test
    public void testLoadResults() {
        result.postValue(Resource.success(TestUtil.getMovieList()));

        onView(listMatcher().atPosition(0)).check(matches(isDisplayed()));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testShowError() {
        result.postValue(Resource.error("Failed to load data", null));
        onView(withId(R.id.tvError)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @NonNull
    private RecyclerViewMatcher listMatcher() {
        return new RecyclerViewMatcher(R.id.recyclerView);
    }

}
