package com.thomaskioko.livedatademo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.repository.model.Movie;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<Movie> movies;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (movies != null)
            return movies.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (movies != null)
            return movies.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (movies != null)
            return movies.get(position).id;
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final View root = LayoutInflater.from(context)
                .inflate(R.layout.search_item, viewGroup, false);
        if (movies != null) {
            final Movie movie = movies.get(position);
            ImageView thumb = root.findViewById(R.id.movie_thumb);
            TextView title = root.findViewById(R.id.movie_title);
            title.setText(movie.title);

            String posterUrl = thumb.getContext().getString(R.string.tmdb_image_url) +
                    thumb.getContext().getString(R.string.image_size_780) + movie.posterUrl;

            Glide.with(thumb.getContext())
                    .load(posterUrl)
                    .into(thumb);
            root.setOnClickListener(view1 -> {
               //TODO:: Navigate to DetailView
            });
        }
        return root;
    }
}
