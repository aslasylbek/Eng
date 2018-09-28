package com.uibenglish.aslan.mvpmindorkssample.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.Main2Activity;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.uibenglish.aslan.mvpmindorkssample.ui.login.LoginActivity;


public class SplashActivity extends BaseActivity implements SplashMvpView {


    SplashPresenter splashPresenter;


    public static Intent getStartIntent(Context context){
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void init(@Nullable Bundle state) {
        DataManager manager = ((MvpApp)getApplication()).getDataManager();
        splashPresenter = new SplashPresenter(manager);
        splashPresenter.attachView(this);
        splashPresenter.decideNextActivty();
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_splash;
    }

    @Override
    public void openMainActivity() {
        Intent intent = Main2Activity.getStartIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(this);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onDestroy() {
        splashPresenter.detachView();
        super.onDestroy();
    }
}
