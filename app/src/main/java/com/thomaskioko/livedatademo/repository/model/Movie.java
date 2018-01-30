package com.thomaskioko.livedatademo.repository.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thomaskioko.livedatademo.db.TmdbTypeConverters;


@Entity(indices = {@Index("id")},
        primaryKeys = {"id"})
@TypeConverters(TmdbTypeConverters.class)
public class Movie {

    @SerializedName("id")
    @Expose
    @NonNull
    public final int id;
    @SerializedName(value = "poster_path")
    public String posterUrl;
    public Double rating;
    @SerializedName(value = "release_date")
    public String releaseYear;
    public String title;


    public Movie(int id, String posterUrl, Double rating, String releaseYear, String title){
        this.id = id;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.title = title;
    }

}
