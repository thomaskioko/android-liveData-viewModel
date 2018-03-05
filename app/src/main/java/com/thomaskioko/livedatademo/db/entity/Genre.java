package com.thomaskioko.livedatademo.db.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(indices = {@Index("id")},
        primaryKeys = {"id"})
public class Genre {

    @NonNull
    public int id;
    public String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", name = " + name + "]";
    }
}
