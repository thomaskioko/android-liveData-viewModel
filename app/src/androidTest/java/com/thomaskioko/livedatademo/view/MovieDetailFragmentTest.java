package com.thomaskioko.livedatademo.view;

import android.arch.lifecycle.MutableLiveData;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.repository.model.Movie;
import com.thomaskioko.livedatademo.testing.SingleFragmentActivity;
import com.thomaskioko.livedatademo.util.EspressoTestUtil;
import com.thomaskioko.livedatademo.util.TestUtil;
import com.thomaskioko.livedatademo.util.ViewModelUtil;
import com.thomaskioko.livedatademo.view.ui.fragment.MovieDetailFragment;
import com.thomaskioko.livedatademo.viewmodel.MovieDetailViewModel;
import com.thomaskioko.livedatademo.vo.Resource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MovieDetailFragmentTest {
    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule =
            new ActivityTestRule<>(SingleFragmentActivity.class, true, true);

    private MovieDetailViewModel viewModel;
    private MutableLiveData<Resource<Movie>> data = new MutableLiveData<>();

    @Before
    public void init() throws Throwable {
        EspressoTestUtil.disableProgressBarAnimations(activityRule);
        MovieDetailFragment fragment = MovieDetailFragment.create(198663);
        viewModel = mock(MovieDetailViewModel.class);
        when(viewModel.getMovie()).thenReturn(data);
        doNothing().when(viewModel).setMovieId(anyInt());

        fragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
        activityRule.getActivity().setFragment(fragment);
    }

    @Test
    public void loading() {
        data.postValue(Resource.loading(null));
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void testMovieDetailsShown() {
        Movie movie = TestUtil.getMovieList().get(0);

        data.postValue(Resource.success(movie));
        onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(movie.title))));
        onView(withId(R.id.movie_detail_plot))
                .check(matches(withText(movie.overview)));
        onView(withId(R.id.year_title))
                .check(matches(withText(movie.releaseYear)));
        onView(withId(R.id.vote_count))
                .check(matches(withText(String.valueOf(movie.voteAverage))));

    }

    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }
}
