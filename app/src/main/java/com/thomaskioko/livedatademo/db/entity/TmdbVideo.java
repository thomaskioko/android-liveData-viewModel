package com.thomaskioko.livedatademo.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 */

@Entity(indices = {@Index("id")},
        primaryKeys = {"id"})
public class TmdbVideo {

    @SerializedName("id")
    @Expose
    @NonNull
    public String id;
    public String name;
    public String type;
    public String key;
    public int size;
    public String site;
    public String iso_639_1;
    public String iso_3166_1;
    @JsonIgnore
    public int movieId;

    public TmdbVideo(String id, String name, String type, String key, int size, String site, String iso_639_1,
                     String iso_3166_1, int movieId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.key = key;
        this.size = size;
        this.site = site;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "ClassPojo [site = " + site + ", id = " + id + ", iso_639_1 = " + iso_639_1 + ", name = " + name + ", type = " + type + ", key = " + key + ", iso_3166_1 = " + iso_3166_1 + ", size = " + size + "]";
    }
}
