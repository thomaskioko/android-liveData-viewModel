package com.thomaskioko.livedatademo.db;

import android.annotation.SuppressLint;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.util.StringUtil;

import java.util.Collections;
import java.util.List;

@SuppressLint("RestrictedApi")
public class TmdbTypeConverters {
    @TypeConverter
    public static List<Integer> stringToIntList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        return StringUtil.splitToIntList(data);
    }

    @TypeConverter
    public static String intListToString(List<Integer> ints) {
        return StringUtil.joinIntoString(ints);
    }
}
