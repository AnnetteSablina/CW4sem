package com.annette.cw.dao;

import com.annette.cw.entity.Organization;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface OrganizationDAO {
    @GET("/organizations")
    Call<List<Organization>> getOrganizations();

    @GET("/organizations/{id}")
    Call<Organization> getOrganization(@Path("id") Integer id);
}
