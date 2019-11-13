package com.uibenglish.aslan.mvpmindorkssample.data.remote;

import android.support.annotation.NonNull;

import com.uibenglish.aslan.mvpmindorkssample.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiFactory {

    public static String apiBaseUrl = BuildConfig.API_ENDPOINT_MOODLE;
    private static OkHttpClient sClient;
    private static volatile ApiService mApiService;

    public ApiFactory() {
    }

    @NonNull
    public static ApiService getApiService(){
        ApiService service = mApiService;
        if (service==null){
            synchronized (ApiService.class){
                service = mApiService;
                if (service==null){
                    service = mApiService = builder.create(ApiService.class);
                }
            }
        }
        return service;
    }

    public static void recreate(){
        sClient = null;
        sClient = getClient();
        mApiService = builder.create(ApiService.class);
    }

    /*@NonNull
    private static Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
*/
    private static Retrofit builder =
            new Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void changeApiBaseUrl(String newApiBaseUrl){
        apiBaseUrl = newApiBaseUrl;
        builder = new Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .client(buildClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient(){
        OkHttpClient client = sClient;
        if (client==null){
            synchronized (ApiFactory.class){
                client = sClient;
                if (client==null){
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor.create())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }
}
