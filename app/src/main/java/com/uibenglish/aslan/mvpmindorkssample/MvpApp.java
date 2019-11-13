package com.uibenglish.aslan.mvpmindorkssample;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.danikula.videocache.HttpProxyCacheServer;
import com.uibenglish.aslan.mvpmindorkssample.audio.AudioFileNameGenerator;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.local.AppDatabase;
import com.uibenglish.aslan.mvpmindorkssample.data.local.SharedPrefsHelper;
import com.uibenglish.aslan.mvpmindorkssample.data.remote.ApiFactory;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/**
 * Created by aslan on 17.05.2018.
 */

public class MvpApp extends Application {

    private DataManager dataManager;
    private SharedPrefsHelper sharedPrefsHelper;
    private AppDatabase appDatabase;
    private HttpProxyCacheServer mProxy;

    public static HttpProxyCacheServer getProxy(Context context) {
        MvpApp app = (MvpApp) context.getApplicationContext();
        return app.mProxy == null ? (app.mProxy = app.newProxy(context)) : app.mProxy;
    }

    @NonNull
    private HttpProxyCacheServer newProxy(Context context) {
        return new HttpProxyCacheServer.Builder(context)
                .fileNameGenerator(new AudioFileNameGenerator())
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPrefsHelper = new SharedPrefsHelper(this);
        appDatabase = AppDatabase.getAppDatabase(this);
        dataManager = new DataManager(sharedPrefsHelper, appDatabase);
        ApiFactory.recreate();
//        ViewPump.init(ViewPump.builder()
//                .addInterceptor(new CalligraphyInterceptor(
//                        new CalligraphyConfig.Builder()
//                                .setDefaultFontPath("fonts/Roboto-Light.ttf")
//                                .setFontAttrId(R.attr.fontPath)
//                                .build())).build());

    }

    public DataManager getDataManager(){
        if (dataManager==null){
            sharedPrefsHelper = new SharedPrefsHelper(this);
            appDatabase = AppDatabase.getAppDatabase(this);

            dataManager = new DataManager(sharedPrefsHelper, appDatabase);
        }
        return dataManager;
    }

}
