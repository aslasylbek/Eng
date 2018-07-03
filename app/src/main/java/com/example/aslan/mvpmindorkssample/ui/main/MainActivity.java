package com.example.aslan.mvpmindorkssample.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.ui.splash.SplashActivity;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainMvpView {

    @BindView(R.id.textView) TextView textViewShow;
    @BindView(R.id.btnBack) Button buttonLogout;
    private MainPresenter mainPresenter;

    public static Intent getStartIntent(Context context){
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void init(@Nullable Bundle state) {
        DataManager manager = ((MvpApp)getApplication()).getDataManager();
        mainPresenter = new MainPresenter(manager);
        mainPresenter.attachView(this);
        textViewShow.setText(mainPresenter.getEmailId());
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btnBack)
    public void logOut(){
        mainPresenter.setUserLoggedOut();
    }

    @Override
    public void openSlashActivity() {
        Intent intent = SplashActivity.getStartIntent(this);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }
}
