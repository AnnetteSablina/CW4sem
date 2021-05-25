package com.annette.cw.service;

import com.annette.cw.dao.DecisionDAO;
import com.annette.cw.dao.OrganizationDAO;
import com.annette.cw.dao.UserDAO;
import com.annette.cw.entity.Organization;
import com.annette.cw.entity.User;
import com.annette.cw.entity.dto.AuthenticationResponse;
import com.annette.cw.entity.dto.LoginRequest;
import com.annette.cw.entity.dto.UserPayload;
import com.annette.cw.utility.AsyncService;
import com.annette.cw.utility.Result;
import com.annette.cw.utility.ServiceProvider;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class Provider {
    private volatile static Provider instance;
    private UserDAO userDAO;
    private DecisionDAO decisionDAO;
    private OrganizationDAO organizationDAO;
    private AsyncService asyncService;

    public Provider() {
        var sp = ServiceProvider.getInstance();
        userDAO = sp.getUserDAO();
        decisionDAO = sp.getDecisionDAO();
        organizationDAO = sp.getOrganizationDAO();
        asyncService = sp.getService();
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

    public void getUserByToken(String token, Consumer<Result<User>> callback) {
        ServiceProvider.getInstance().updateToken(token);
        Call<User> call = ServiceProvider.getInstance().getUserDAO().getSelf(token);
        execute(call, callback);
    }

    public void updateCurrentUser(String userName, String password, String email, String name, String surname,
                                  Integer organizationId, Consumer<Result<User>> callback) {
        Call<User> call = ServiceProvider.getInstance().getUserDAO().updateCurrentUser(
                new UserPayload(userName,password,email,name,surname,organizationId));
        execute(call,callback);
    }
    public void getOrganizations(Consumer<Result<List<Organization>>> callback){
        Call<List<Organization>> call = ServiceProvider.getInstance().getOrganizationDAO().getOrganizations();
        execute(call,callback);
    }

    private <T> void execute(Call<T> call, Consumer<Result<T>> callback) {
        Result<T> result = new Result<>();
        asyncService.execute(() -> {
            try {
                Response<T> res = call.execute();
                result.setCode(res.code());
                if (res.code() == 403) {
                    result.setObjectExist(false);
                    return;
                }
                if (res.body() == null) result.setServerError(true);
                if (res.code() == 200) { result.setObjectExist(true);
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
}
