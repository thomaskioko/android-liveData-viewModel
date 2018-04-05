package com.thomaskioko.livedatademo.view.callback;

import android.widget.ImageView;

import com.thomaskioko.livedatademo.db.entity.Movie;

/**
 *
 */

public interface MovieCallback {
    void onClick(ImageView sharedImageView, Movie movie);
}

