package com.thomaskioko.livedatademo.db.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(indices = {@Index("id")},
        primaryKeys = {"id"})
public class Genre {

    @NonNull
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", name = " + name + "]";
    }
}
