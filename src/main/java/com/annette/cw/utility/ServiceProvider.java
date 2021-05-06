package com.annette.cw.utility;

import com.annette.cw.controller.DecisionService;
import com.annette.cw.controller.OrganizationService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class ServiceProvider {
    private volatile static ServiceProvider instance;
    private AsyncService service = new AsyncService();
    private AuthInterceptor interceptor = new AuthInterceptor();
    private UserService userService;
    private DecisionService decisionService;
    private OrganizationService organizationService;

    public UserService getUserService() {
        return userService;
    }

    public AsyncService getService() {
        return service;
    }

    public DecisionService getDecisionService() {
        return decisionService;
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public ServiceProvider() {
        var client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        var retrofit = new Retrofit.Builder()
                .baseUrl("https://cw4sem-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        userService = retrofit.create(UserService.class);
        decisionService = retrofit.create(DecisionService.class);
        organizationService = retrofit.create(OrganizationService.class);
    }

    public static ServiceProvider getInstance() {
        if (instance == null) {
            synchronized (ServiceProvider.class) {
                if (instance == null) {
                    instance = new ServiceProvider();
                }
            }
        }
        return instance;
    }
    public void updateToken(String token) {
        interceptor.setKey(token);
    }
}
