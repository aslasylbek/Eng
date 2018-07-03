package com.example.aslan.mvpmindorkssample.data.remote;

import com.example.aslan.mvpmindorkssample.data.content.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("mobile/login.php")
    Call<LoginResponse> getValidationInfo(@Field("username") String username, @Field("password") String password);

}
