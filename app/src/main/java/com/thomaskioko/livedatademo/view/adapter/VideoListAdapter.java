package com.thomaskioko.livedatademo.view.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thomaskioko.livedatademo.R;
import com.thomaskioko.livedatademo.db.entity.TmdbVideo;
import com.thomaskioko.livedatademo.view.callback.VideoCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Thomas Kioko
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MovieViewHolder> {

    private List<TmdbVideo> mVideoList = new ArrayList<>();
    private VideoCallback mVideoClickCallback;

    public VideoListAdapter(VideoCallback videoClickCallback) {
        mVideoClickCallback = videoClickCallback;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_trailer, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        TmdbVideo video = mVideoList.get(position);

        String videoUrl = holder.ivPoster.getContext()
                .getResources().getString(R.string.thumbnail_max_resolution, video.key);

        holder.tvVideoTitle.setText(video.name);
        Glide.with(holder.ivPoster.getContext())
                .load(videoUrl)
                .into(holder.ivPoster);

        holder.itemView.setOnClickListener(view -> {
            // <--- Giving time to the ripple effect finish before opening a new activity
            new Handler().postDelayed(() -> mVideoClickCallback.onClick(holder.ivPoster, video), 200);
        });

    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public void setData(List<TmdbVideo> movieList) {
        mVideoList = movieList;
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        mVideoList.clear();
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_video_background)
        ImageView ivPoster;
        @BindView(R.id.text_view_video_title)
        TextView tvVideoTitle;
        View itemView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.itemView = itemView;
        }
    }

}
