package com.example.aslan.mvpmindorkssample.data.remote;

import com.example.aslan.mvpmindorkssample.data.content.LoginResponse;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiService {

    @FormUrlEncoded
    @POST("mobile/login.php")
    Call<LoginResponse> getValidationInfo(@Field("username") String username, @Field("password") String password);

    @GET
    Call<TranslationResponse> getWordTranslate(@Url String url);

}
