package com.thomaskioko.livedatademo.view.callback;

import android.widget.ImageView;

import com.thomaskioko.livedatademo.db.entity.TmdbVideo;

/**
 *
 */

public interface VideoCallback {
    void onClick(ImageView sharedImageView, TmdbVideo tmdbVideo);
}

