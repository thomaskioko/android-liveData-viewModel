package com.thomaskioko.livedatademo.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thomaskioko.livedatademo.db.TmdbTypeConverters;

import java.util.List;


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
    public Boolean adult;
    public String overview;
    @SerializedName(value = "original_title")
    public String originalTitle;
    @SerializedName(value = "original_language")
    public String originalLanguage;
    @SerializedName(value = "backdrop_path")
    public String backdropPath;
    public Double popularity;
    @SerializedName(value = "vote_count")
    public Integer voteCount;
    public Boolean video;
    @SerializedName(value = "vote_average")
    public Double voteAverage;
    @SerializedName(value = "genre_ids")
    public List<Integer> genreIds;

    public Movie(int id, String posterUrl, Double rating, String releaseYear, String title, Boolean adult,
                 String overview, String originalTitle, String originalLanguage, String backdropPath,
                 Double popularity, Integer voteCount, Boolean video, Double voteAverage, List<Integer> genreIds){
        this.id = id;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.title = title;
        this.adult = adult;
        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.genreIds = genreIds;
    }

}
