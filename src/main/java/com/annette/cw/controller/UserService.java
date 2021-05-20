package com.annette.cw.controller;

import com.annette.cw.entity.dto.LoginRequest;
import com.annette.cw.entity.User;
import com.annette.cw.entity.dto.AuthenticationResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserService {
    @GET("users/projection")
    Call<List<User>> getUsers();

    @POST("/auth/login")
    Call<AuthenticationResponse> logIn(@Body LoginRequest loginRequest);

    @GET("/users/self")
    Call<AuthenticationResponse> getSelf(@Header("Authorization") String token);

}
