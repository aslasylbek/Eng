package com.example.aslan.mvpmindorkssample.data.remote;

import com.example.aslan.mvpmindorkssample.data.content.EngInformationResponse;
import com.example.aslan.mvpmindorkssample.data.content.LoginResponse;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.mindorks.placeholderview.annotations.Position;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @FormUrlEncoded
    @POST("mobile_eng/data.php")
    Call<EngInformationResponse> getStudentSubjectInformation(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("modules/online_lessons/add_results.php")
    Call<String> postGrammarResult(@Field("exercise_id") String ex_id, @Field("user_id") String user_id, @Field("topic_id") int topic_id, @Field("result") int result);


    @POST("modules/online_lessons/add_results.php")
    Call<String> postTaskResult(@Body String data);

}
