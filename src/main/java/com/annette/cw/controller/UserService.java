package com.annette.cw.controller;

import com.annette.cw.ditch.Greeting;
import com.annette.cw.ditch.User;
import com.annette.cw.entity.dto.AuthenticationResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface UserService {
    @GET("users/projection")
    Call<List<User>> getUsers();

    @GET("/greeting")
    Call<Greeting> getGreeting();

    @FormUrlEncoded
    @POST("/auth/login")
    Call<AuthenticationResponse> logIn(@Field("username") String username,
                                       @Field("password") String password);

}
