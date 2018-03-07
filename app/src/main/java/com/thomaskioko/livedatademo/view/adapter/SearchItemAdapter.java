package com.thomaskioko.livedatademo.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.db.entity.Movie;
import com.thomaskioko.livedatademo.view.callback.MovieCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> {

    private List<Movie> mMovieList = new ArrayList<>();
    private MovieCallback mMovieClickCallback;


    public SearchItemAdapter(MovieCallback movieClickCallback) {
        mMovieClickCallback = movieClickCallback;
    }

    @Override
    public SearchItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_movie_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchItemAdapter.ViewHolder holder, int position) {
        final Movie movie = mMovieList.get(position);

        String posterUrl = holder.imageView.getContext().getString(R.string.tmdb_image_url) +
                holder.imageView.getContext().getString(R.string.image_size_780) + movie.posterUrl;

        holder.tvName.setText(movie.title);
        holder.releaseYear.setText(movie.releaseYear);
        holder.itemView.setOnClickListener(view -> mMovieClickCallback.onClick(holder.imageView, movie));
        Glide.with(holder.imageView.getContext())
                .load(posterUrl)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_title)
        TextView tvName;
        @BindView(R.id.movie_thumb)
        ImageView imageView;
        @BindView(R.id.movie_release_year)
        TextView releaseYear;
        View itemView;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.itemView = view;
        }
    }


    public void setItems(List<Movie> movieList) {
        mMovieList = movieList;
        notifyDataSetChanged();
    }


}
