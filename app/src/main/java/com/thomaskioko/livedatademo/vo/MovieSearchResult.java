package com.thomaskioko.livedatademo.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thomaskioko.livedatademo.db.TmdbTypeConverters;

import java.util.List;


@Entity(primaryKeys = {"query"})
@TypeConverters(TmdbTypeConverters.class)
public class MovieSearchResult {
    @NonNull
    public final String query;
    public final List<Integer> repoIds;
    public final int totalCount;
    @Nullable
    public final Integer next;

    public MovieSearchResult(@NonNull String query, List<Integer> repoIds, int totalCount,
                             @Nullable Integer next) {
        this.query = query;
        this.repoIds = repoIds;
        this.totalCount = totalCount;
        this.next = next;
    }
}
