package com.annette.cw.controller;

import com.annette.cw.ditch.Greeting;
import com.annette.cw.entity.Decision;
import com.annette.cw.entity.dto.AuthenticationResponse;
import com.annette.cw.entity.dto.DecisionPayload;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface DecisionService {
    @GET("/decisions")
    Call<List<Decision>> getDecisions();

    @GET("/decisions/{id}")
    Call<Decision> getDecision(@Path("id") Integer id);

    @DELETE("/decisions/{id}")
    Call<String> deleteDecision(@Path("id") Integer id);

    @POST("/decisions/update/{id}")
    Call<Decision> updateDecision(@Body DecisionPayload payload, @Path("id") Integer id);

    @POST("/decisions/add")
    Call<Decision> addDecision(@Body DecisionPayload payload);
}
