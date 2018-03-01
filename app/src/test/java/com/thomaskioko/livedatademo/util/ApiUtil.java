package com.thomaskioko.livedatademo.util;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.thomaskioko.livedatademo.repository.model.ApiResponse;

import retrofit2.Response;

public class ApiUtil {
    public static <T> LiveData<ApiResponse<T>> successCall(T data) {
        return createCall(Response.success(data));
    }
    public static <T> LiveData<ApiResponse<T>> createCall(Response<T> response) {
        MutableLiveData<ApiResponse<T>> data = new MutableLiveData<>();
        data.setValue(new ApiResponse<>(response));
        return data;
    }
}
