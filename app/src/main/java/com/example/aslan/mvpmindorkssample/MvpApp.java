package com.example.aslan.mvpmindorkssample;

import android.app.Application;

import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.local.SharedPrefsHelper;
import com.example.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.squareup.leakcanary.LeakCanary;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/**
 * Created by aslan on 17.05.2018.
 */

public class MvpApp extends Application {

    DataManager dataManager;
    SharedPrefsHelper sharedPrefsHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this))
            return;
        LeakCanary.install(this);

        sharedPrefsHelper = new SharedPrefsHelper(this);
        dataManager = new DataManager(sharedPrefsHelper);
        ApiFactory.recreate();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Roboto-Light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build())).
                        build());
    }

    public DataManager getDataManager(){
        if (dataManager==null){
            sharedPrefsHelper = new SharedPrefsHelper(this);
            dataManager = new DataManager(sharedPrefsHelper);
        }
        return dataManager;
    }

}
