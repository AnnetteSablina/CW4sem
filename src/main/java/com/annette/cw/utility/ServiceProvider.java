package com.annette.cw.utility;

import com.annette.cw.dao.DecisionDAO;
import com.annette.cw.dao.OrganizationDAO;
import com.annette.cw.dao.UserDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class ServiceProvider {
    private volatile static ServiceProvider instance;
    private AsyncService service = new AsyncService();
    private AuthInterceptor interceptor = new AuthInterceptor();
    private UserDAO userDAO;
    private DecisionDAO decisionDAO;
    private OrganizationDAO organizationDAO;
    private Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class,new InstantTypeConverter()).create();

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public Gson getGson() { return gson; }

    public AsyncService getService() {
        return service;
    }

    public DecisionDAO getDecisionDAO() {
        return decisionDAO;
    }

    public OrganizationDAO getOrganizationDAO() {
        return organizationDAO;
    }

    private ServiceProvider() {
        var client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        var retrofit = new Retrofit.Builder()
                .baseUrl("https://cw4sem-server.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        userDAO = retrofit.create(UserDAO.class);
        decisionDAO = retrofit.create(DecisionDAO.class);
        organizationDAO = retrofit.create(OrganizationDAO.class);
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
