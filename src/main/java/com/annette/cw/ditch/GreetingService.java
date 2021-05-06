package com.annette.cw.ditch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GreetingService {
    @GET("/greeting")
    Call<Greeting> getGreeting(@Query("name") String name);
}
