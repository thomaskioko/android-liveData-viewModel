package com.thomaskioko.livedatademo.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.repository.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private List<Movie> mMovieList = new ArrayList<>();

    public MovieListAdapter() {
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie movie = mMovieList.get(position);
        String posterUrl = holder.ivPoster.getContext().getString(R.string.tmdb_image_url) +
                holder.ivPoster.getContext().getString(R.string.image_size_780) + movie.posterUrl;

        Glide.with(holder.ivPoster.getContext())
                .load(posterUrl)
                .into(holder.ivPoster);

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void setData(List<Movie> movieList) {
        mMovieList = movieList;
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        mMovieList.clear();
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPoster)
        public ImageView ivPoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
