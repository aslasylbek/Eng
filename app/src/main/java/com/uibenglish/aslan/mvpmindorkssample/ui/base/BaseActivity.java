package com.uibenglish.aslan.mvpmindorkssample.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.uibenglish.aslan.mvpmindorkssample.MvpApp;
import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.general.LoadingDialog;
import com.uibenglish.aslan.mvpmindorkssample.general.LoadingView;
import com.uibenglish.aslan.mvpmindorkssample.utils.NetworkUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created by aslan on 17.05.2018.
 */

/*
@Todo potom dopishu
 */
public abstract class BaseActivity extends AppCompatActivity implements MvpView, BaseFragment.Callback {
    private LoadingView mLoadingView;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResource());
        mUnbinder = ButterKnife.bind(this);
        init(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    protected abstract void init(@Nullable Bundle state);

    @LayoutRes
    protected abstract int getContentResource();

    @Override
    public void showLoading() {
        hideLoading();
        if (mLoadingView==null){
            mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        }
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        if (mLoadingView!=null)
            mLoadingView.hideLoading();
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void showToastMessage(int resId) {
        Toast.makeText(getApplicationContext(),
                getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onDestroy() {
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        super.onDestroy();
    }



}
