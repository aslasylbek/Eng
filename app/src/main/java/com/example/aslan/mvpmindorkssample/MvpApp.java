package com.example.aslan.mvpmindorkssample;

import android.app.Application;

import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.local.SharedPrefsHelper;
import com.example.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by aslan on 17.05.2018.
 */

public class MvpApp extends Application {

    DataManager dataManager;
    SharedPrefsHelper sharedPrefsHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        sharedPrefsHelper = new SharedPrefsHelper(this);
        dataManager = new DataManager(sharedPrefsHelper);
        ApiFactory.recreate();
    }

    public DataManager getDataManager(){
        if (dataManager==null){
            sharedPrefsHelper = new SharedPrefsHelper(this);
            dataManager = new DataManager(sharedPrefsHelper);
        }
        return dataManager;
    }

}
