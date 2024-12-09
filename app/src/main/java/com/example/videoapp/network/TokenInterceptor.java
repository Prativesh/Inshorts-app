package com.example.videoapp.network;

import com.example.videoapp.BuildConfig;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + BuildConfig.MOVIEDB_API_ACCESS_KEY)
                .build();
        return chain.proceed(newRequest);
    }
}
