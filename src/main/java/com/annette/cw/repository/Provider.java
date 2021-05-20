package com.annette.cw.repository;

import com.annette.cw.controller.DecisionService;
import com.annette.cw.controller.OrganizationService;
import com.annette.cw.controller.UserService;
import com.annette.cw.entity.dto.AuthenticationResponse;
import com.annette.cw.entity.dto.LoginRequest;
import com.annette.cw.utility.AsyncService;
import com.annette.cw.utility.Result;
import com.annette.cw.utility.ServiceProvider;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.function.Consumer;

public class Provider {
    private volatile static Provider instance;
    private UserService userService;
    private DecisionService decisionService;
    private OrganizationService organizationService;
    private AsyncService asyncService;

    public Provider() {
        var sp = ServiceProvider.getInstance();
        userService = sp.getUserService();
        decisionService = sp.getDecisionService();
        organizationService = sp.getOrganizationService();
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
                getUserService().logIn(new LoginRequest(login, password));
        execute(call, callback);
    }
    public void getUserByToken(String token,Consumer<Result<AuthenticationResponse>> callback){
        ServiceProvider.getInstance().updateToken(token);
        Call<AuthenticationResponse> call = ServiceProvider.getInstance().getUserService().getSelf(token);
        execute(call,callback);
    }

    private <T> void execute(Call<T> call, Consumer<Result<T>> callback) {
        Result<T> result = new Result<>();
        asyncService.execute(() -> {
            try {
                Response<T> res = call.execute();
                result.setCode(res.code());
                if (res.code() == 403){ result.setObjectExist(false); return;}
                if (res.body() == null) result.setServerError(true);
                if (res.code() == 200) result.setResult(res.body());
            } catch (IOException e) {
                result.setServerError(true);
            } finally {
                callback.accept(result);
            }
        });
    }
}
