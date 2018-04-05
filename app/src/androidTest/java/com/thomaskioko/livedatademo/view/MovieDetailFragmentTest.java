package com.thomaskioko.livedatademo.view;

import android.arch.lifecycle.MutableLiveData;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.db.entity.Movie;
import com.thomaskioko.livedatademo.db.entity.TmdbVideo;
import com.thomaskioko.livedatademo.testing.SingleFragmentActivity;
import com.thomaskioko.livedatademo.util.EspressoTestUtil;
import com.thomaskioko.livedatademo.util.MatcherUtil;
import com.thomaskioko.livedatademo.util.TestUtil;
import com.thomaskioko.livedatademo.util.ViewModelUtil;
import com.thomaskioko.livedatademo.view.ui.fragment.MovieDetailFragment;
import com.thomaskioko.livedatademo.viewmodel.MovieDetailViewModel;
import com.thomaskioko.livedatademo.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
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
    private MutableLiveData<Resource<List<TmdbVideo>>> videoData = new MutableLiveData<>();

    @Before
    public void init()  {
        EspressoTestUtil.disableProgressBarAnimations(activityRule);
        MovieDetailFragment fragment = MovieDetailFragment.create(anyInt(), anyInt());
        viewModel = mock(MovieDetailViewModel.class);
        when(viewModel.getMovie()).thenReturn(data);
        when(viewModel.getVideoMovies()).thenReturn(videoData);
        doNothing().when(viewModel).setMovieId(anyInt());

        fragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
        activityRule.getActivity().setFragment(fragment);
    }

    @Test
    public void loading() {
        data.postValue(Resource.loading(null));

        //Verify that progressbar is set
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void testMovieDetailsShown() {
        Movie movie = TestUtil.getMovieList().get(0);

        data.postValue(Resource.success(movie));

        //Verify that the progressbar is not shown
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));

        //Verify that the error message is not shown
        onView(withId(R.id.error_msg)).check(matches(not(isDisplayed())));

        //Verify that the movie title is set
        onView(withId(R.id.title)).check(matches(MatcherUtil.withText(movie.title)));

        String movieRating = NumberFormat.getInstance(Locale.getDefault()).format(movie.rating);

        //Verify that text rating is set
        onView(withId(R.id.rating_text)).check(matches(MatcherUtil.withText(movieRating)));

        //Verify that movie overview is set
        onView(withId(R.id.movie_detail_plot)).check(matches(MatcherUtil.withText(movie.overview)));

        //Verify that the movie release year is set
        onView(withId(R.id.year_title)).check(matches(MatcherUtil.withText(movie.releaseYear)));

    }

    @Test
    public void testDisplayMovieVideosOnSuccess(){
        List<TmdbVideo> tmdbVideoList = TestUtil.getTmdbVideoList();
        videoData.postValue(Resource.success(tmdbVideoList));

        //Verify that the progressbar is not shown
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));

        //Verify that the error message is not shown
        onView(withId(R.id.error_msg)).check(matches(not(isDisplayed())));

        //verify that the recycler view has at least an item displayed
        onView(MatcherUtil.listMatcher(R.id.recyclerview_trailer).atPosition(0)).check(matches(isDisplayed()));

    }

    @Test
    public void testShowVideoError() {
        videoData.postValue(Resource.error("Failed to load data", null));

        //Verify that Error message is shown
        onView(withId(R.id.error_msg)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void testMovieDetailShowError() {
        data.postValue(Resource.error("Failed to load data", null));

        //Verify that Error message is shown
        onView(withId(R.id.error_msg)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
