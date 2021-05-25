package com.annette.cw.dao;

import com.annette.cw.entity.Organization;
import com.annette.cw.entity.dto.OrganizationPayload;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface OrganizationDAO {
    @GET("/organizations")
    Call<List<Organization>> getOrganizations();

    @GET("/organizations/{id}")
    Call<Organization> getOrganization(@Path("id") Integer id);

    @DELETE("/organizations/{id}")
    Call<Void> deleteOrganization(@Path("id") Integer id);

    @POST("/organizations/update/{id}")
    Call<Organization> updateOrganization(@Body OrganizationPayload payload,
                                          @Path("id") Integer id);
    @POST("organizations/add")
    Call<Organization> addOrganization(@Body OrganizationPayload payload);
}