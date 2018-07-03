package com.example.aslan.mvpmindorkssample.data.remote;

import android.support.annotation.NonNull;

import com.example.aslan.mvpmindorkssample.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiFactory {

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
                    service = mApiService = buildRetrofit().create(ApiService.class);
                }
            }
        }
        return service;
    }

    public static void recreate(){
        sClient = null;
        sClient = getClient();
        mApiService = buildRetrofit().create(ApiService.class);
    }

    @NonNull
    private static Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(getClient())
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
                .build();
    }
}
