package com.annette.cw.service;

import com.annette.cw.controller.Controller;
import com.annette.cw.dao.DecisionDAO;
import com.annette.cw.dao.OrganizationDAO;
import com.annette.cw.dao.UserDAO;
import com.annette.cw.entity.Decision;
import com.annette.cw.entity.DecisionRecord;
import com.annette.cw.entity.Organization;
import com.annette.cw.entity.User;
import com.annette.cw.entity.dto.*;
import com.annette.cw.utility.AsyncService;
import com.annette.cw.utility.Result;
import com.annette.cw.utility.ServiceProvider;
import com.google.gson.Gson;
import lombok.Data;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;

@Data
public class Provider {
    private volatile static Provider instance;
    private UserDAO userDAO;
    private DecisionDAO decisionDAO;
    private OrganizationDAO organizationDAO;
    private AsyncService asyncService;
    private Gson gson;

    public Provider() {
        var sp = ServiceProvider.getInstance();
        userDAO = sp.getUserDAO();
        decisionDAO = sp.getDecisionDAO();
        organizationDAO = sp.getOrganizationDAO();
        asyncService = sp.getService();
        gson = sp.getGson();
    }

    public static Provider getInstance() {
        if (instance == null) {
            synchronized (ServiceProvider.class) {
                if (instance == null) {
                    instance = new Provider();
                }
            }
        }
        return instance;
    }

    public void logIn(String login, String password, Consumer<Result<AuthenticationResponse>> callback) {
        Call<AuthenticationResponse> call = ServiceProvider.getInstance().
                getUserDAO().logIn(new LoginRequest(login, password));
        execute(call, callback);
    }

    public void signUp(String userName, String password, String email, String name, String surname,
                       Integer organizationId, Consumer<Result<AuthenticationResponse>> callback) {
        Call<AuthenticationResponse> call = ServiceProvider.getInstance().getUserDAO().signUp(
                new UserPayload(userName, password, email, name, surname, organizationId));
        execute(call, callback);
    }


    public void getUserByToken(String token, Consumer<Result<User>> callback) {
        ServiceProvider.getInstance().updateToken(token);
        Call<User> call = ServiceProvider.getInstance().getUserDAO().getSelf(token);
        execute(call, callback);
    }

    public void updateCurrentUser(String userName, String password, String email, String name, String surname,
                                  Integer organizationId, Consumer<Result<User>> callback) {
        Call<User> call = ServiceProvider.getInstance().getUserDAO().updateCurrentUser(
                new UserPayload(userName, password, email, name, surname, organizationId));
        execute(call, callback);
    }
    public void promoteUser(Integer userId, Consumer<Result<User>> callback) {
        Call<User> call = ServiceProvider.getInstance().getUserDAO().promoteUser(userId);
        execute(call, callback);
    }

    public void updateUser(String userName, String password, String email, String name, String surname,
                           Integer organizationId, Integer userId, Consumer<Result<User>> callback) {
        Call<User> call = ServiceProvider.getInstance().getUserDAO().updateUser(
                new UserPayload(userName, password, email, name, surname, organizationId), userId);
        execute(call, callback);
    }

    public void updateOrganization(String name, String type, Integer id, Consumer<Result<Organization>> callback) {
        Call<Organization> call = ServiceProvider.getInstance().getOrganizationDAO().
                updateOrganization(new OrganizationPayload(name, type), id);
        execute(call, callback);
    }

    public void addOrganization(String name, String type, Consumer<Result<Organization>> callback) {
        Call<Organization> call = ServiceProvider.getInstance().getOrganizationDAO().
                addOrganization(new OrganizationPayload(name, type));
        execute(call, callback);
    }

    public void getOrganizations(Consumer<Result<List<Organization>>> callback) {
        Call<List<Organization>> call = ServiceProvider.getInstance().getOrganizationDAO().getOrganizations();
        execute(call, callback);
    }

    public void getOrganization(Integer id, Consumer<Result<Organization>> callback) {
        Call<Organization> call = ServiceProvider.getInstance().getOrganizationDAO().getOrganization(id);
        execute(call, callback);
    }

    public void getUsers(Consumer<Result<List<User>>> callback) {
        Call<List<User>> call = ServiceProvider.getInstance().getUserDAO().getUsers();
        execute(call, callback);
    }

    public void getDecisions(Consumer<Result<List<Decision>>> callback) {
        Call<List<Decision>> call = ServiceProvider.getInstance().getDecisionDAO().getDecisions();
        execute(call, callback);
    }

    public void makeDecisionById(Integer id,List<List<Double>> matrix, Consumer<Result<DecisionRecord>> callback) {
        Call<DecisionRecord> call = ServiceProvider.getInstance().getDecisionDAO().makeDecision(matrix,id);
        execute(call, callback);
    }

    public void updateDecisionById(String name, List<String> strategyList, Integer natureStates, Double coefficient,
                                   Integer userId,Integer decisionId, Consumer<Result<Decision>> callback) {
        var payload = new DecisionPayload(name, "", strategyList, natureStates, coefficient, userId,
                Controller.getInstance().getChangeableDecision().getCreatedDate());
        debugSerialization(payload);
        Call<Decision> call = ServiceProvider.getInstance().getDecisionDAO().
                updateDecision(payload, decisionId);
        execute(call, callback);
    }
    public void getDecisionById(Integer id,Consumer<Result<Decision>> callback){
        Call<Decision> call = ServiceProvider.getInstance().getDecisionDAO().getDecision(id);
        execute(call,callback);
    }

    public void addDecision(String name, List<String> strategyList, Integer natureStates, Double coefficient, Integer id,
                            Consumer<Result<Decision>> callback) {
        Call<Decision> call = ServiceProvider.getInstance().getDecisionDAO().addDecision(new DecisionPayload(name, "",
                strategyList, natureStates, coefficient, id, Instant.now()));
        execute(call, callback);
    }

    private <T> void execute(Call<T> call, Consumer<Result<T>> callback) {
        Result<T> result = new Result<>();
        asyncService.execute(() -> {
            try {
                Response<T> res = call.execute();
                result.setCode(res.code());
                if (res.code() == 403 || res.code() == 400) {
                    result.setObjectExist(false);
                    return;
                }
                if (res.body() == null) result.setServerError(true);
                if (res.code() == 200) {
                    result.setObjectExist(true);
                    result.setResult(res.body());
                    result.setSuccess(true);
                }
            } catch (IOException e) {
                result.setServerError(true);
            } finally {
                System.out.println("Body: " + result.getResult());
                System.out.println("Code: " + result.getCode());
                callback.accept(result);
            }
        });
    }

    private <T> void debugSerialization(T object) {
        String jsonString = gson.toJson(object);
        System.out.println(jsonString);
    }
}
