package com.thomaskioko.livedatademo.api;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.thomaskioko.livedatademo.db.entity.Movie;
import com.thomaskioko.livedatademo.db.entity.TmdbVideo;
import com.thomaskioko.livedatademo.repository.api.MovieResult;
import com.thomaskioko.livedatademo.repository.api.TmdbService;
import com.thomaskioko.livedatademo.repository.api.VideoResult;
import com.thomaskioko.livedatademo.repository.util.LiveDataCallAdapterFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thomaskioko.livedatademo.util.LiveDataTestUtil.getValue;
import static com.thomaskioko.livedatademo.utils.AppConstants.CONNECT_TIMEOUT;
import static com.thomaskioko.livedatademo.utils.AppConstants.READ_TIMEOUT;
import static com.thomaskioko.livedatademo.utils.AppConstants.WRITE_TIMEOUT;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class TmdbServiceTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private TmdbService service;
    private MockWebServer mockWebServer;

    @Before
    public void createdService() throws IOException {
        mockWebServer = new MockWebServer();
        service = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(TmdbService.class);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testGetPopularMovies()  throws IOException, InterruptedException {
        enqueueResponse("popular-movies.json");
        MovieResult movieResult = getValue(service.getPopularMovies()).body;

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/movie/popular?"));

        assertNotNull(movieResult);

        List<Movie> movieList = movieResult.getResults();
        assertTrue(movieList.size()> 0);
    }

    @Test
    public void testGetMovieVideos()  throws IOException, InterruptedException {
        enqueueResponse("videos-by-movie-id.json");
        VideoResult videoResult = getValue(service.getMovieVideos(354912)).body;

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/movie/354912/videos"));

        assertNotNull(videoResult);

        List<TmdbVideo> results = videoResult.getResults();
        assertTrue(results.size()> 0);
    }

    @Test
    public void testGetSearchedMovie()  throws IOException, InterruptedException {
        enqueueResponse("search-movie.json");
        MovieResult movieResult = getValue(service.searchMovies("hitman")).body;

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/search/movie?&query=hitman"));

        assertNotNull(movieResult);

        List<Movie> movieList = movieResult.getResults();
        assertTrue(movieList.size()> 0);
    }

    @Test
    public void testGetSearchedMovieById()  throws IOException, InterruptedException {
        enqueueResponse("search-movie-id.json");
        Movie movieResult = getValue(service.getMovieById(354912)).body;

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/movie/354912"));

        assertNotNull(movieResult);

        assertThat(movieResult.title, is("Coco"));
    }


    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)));
    }

    private OkHttpClient getHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }
}
