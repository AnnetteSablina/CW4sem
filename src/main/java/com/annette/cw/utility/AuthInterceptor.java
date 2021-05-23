package com.annette.cw.utility;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public final class AuthInterceptor implements Interceptor {
    private String key = "";

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest;
        if (!(key == null || key.isEmpty())){
            Headers header = new Headers.Builder().add("Authorization",key).build();
            newRequest = request.newBuilder().headers(header).build();
            return chain.proceed(newRequest);
        } else newRequest = request;
        return chain.proceed(newRequest);
    }

    public String getKey() {
        return key;
    }
}
