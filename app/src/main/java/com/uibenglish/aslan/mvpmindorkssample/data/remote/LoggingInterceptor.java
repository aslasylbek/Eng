package com.uibenglish.aslan.mvpmindorkssample.data.remote;

import android.support.annotation.NonNull;

import com.uibenglish.aslan.mvpmindorkssample.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public final class LoggingInterceptor implements Interceptor{

    private final Interceptor mLoggingInterceptor;

    private LoggingInterceptor() {
        mLoggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }

    @NonNull
    public static Interceptor create(){
        return new LoggingInterceptor();
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        return mLoggingInterceptor.intercept(chain);
    }
}
