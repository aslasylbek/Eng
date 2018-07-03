package com.example.aslan.mvpmindorkssample.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;
import com.example.aslan.mvpmindorkssample.ui.login.LoginActivity;
import com.example.aslan.mvpmindorkssample.ui.main.MainActivity;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashMvpView {


    SplashPresenter splashPresenter;


    public static Intent getStartIntent(Context context){
        return new Intent(context, SplashActivity.class);
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DataManager dataManager = ((MvpApp)getApplication()).getDataManager();
        splashPresenter = new SplashPresenter(dataManager);
        splashPresenter.onAttach(this);
        splashPresenter.decideNextActivty();
    }*/

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
        Intent intent = MainActivity.getStartIntent(this);
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
        super.onDestroy();
        splashPresenter.detachView();
    }
}
