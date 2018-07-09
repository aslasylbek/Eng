package com.example.aslan.mvpmindorkssample.data;

import com.example.aslan.mvpmindorkssample.data.content.LoginResponse;
import com.example.aslan.mvpmindorkssample.data.local.PreferenceHelper;
import com.example.aslan.mvpmindorkssample.data.local.SharedPrefsHelper;
import com.example.aslan.mvpmindorkssample.data.remote.ApiFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aslan on 17.05.2018.
 */

public class DataManager implements DataManagerContract {

    private PreferenceHelper prefsHelper;

    public DataManager(SharedPrefsHelper prefsHelper) {
        this.prefsHelper = prefsHelper;
    }

    public void sendForToken(String username, String password, final GetTokenCallbacks callback){
        ApiFactory
                .getApiService()
                .getValidationInfo(username, password)
                .enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onError();
            }
        });


    }

    @Override
    public void clear() {
        prefsHelper.clear();
    }

    @Override
    public void putToken(String token) {
        prefsHelper.putToken(token);
    }

    @Override
    public boolean getLoggedMode() {
        return prefsHelper.getLoggedMode();
    }

    @Override
    public void putPassword(String password) {
        prefsHelper.putPassword(password);
    }

    @Override
    public String getPrefPassword() {
        return prefsHelper.getPrefPassword();
    }

    @Override
    public String getPrefToken() {
        return prefsHelper.getPrefToken();
    }

    @Override
    public void putUserId(String password) {
        prefsHelper.putUserId(password);
    }

    @Override
    public String getPrefUserid() {
        return prefsHelper.getPrefUserid();
    }

    @Override
    public void putBarcode(String barcode) {
        prefsHelper.putBarcode(barcode);
    }

    @Override
    public String getBarcode() {
        return prefsHelper.getBarcode();
    }

    @Override
    public void setLoggedMode(boolean loggedIn) {
        prefsHelper.setLoggedMode(loggedIn);
    }

    public interface GetTokenCallbacks{
        void onSuccess(LoginResponse response);
        void onError();
    }
}
