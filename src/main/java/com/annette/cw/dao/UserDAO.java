package com.annette.cw.dao;

import com.annette.cw.entity.User;
import com.annette.cw.entity.dto.AuthenticationResponse;
import com.annette.cw.entity.dto.LoginRequest;
import com.annette.cw.entity.dto.UserPayload;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserDAO {
    @GET("/users")
    Call<List<User>> getUsers();

    @POST("/auth/login")
    Call<AuthenticationResponse> logIn(@Body LoginRequest loginRequest);

    @GET("/users/self")
    Call<User> getSelf(@Header("Authorization") String token);

    @POST("/users/update/{id}")
    Call<User> updateUser(@Body UserPayload payload, @Path("id") int id);

    @POST("/users/update/self")
    Call<User> updateCurrentUser(@Body UserPayload payload);


}
