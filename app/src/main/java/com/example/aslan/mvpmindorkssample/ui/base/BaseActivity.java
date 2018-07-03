package com.example.aslan.mvpmindorkssample.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.data.DataManager;

import butterknife.ButterKnife;

/**
 * Created by aslan on 17.05.2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResource());
        ButterKnife.bind(this);
        init(savedInstanceState);
    }

    protected abstract void init(@Nullable Bundle state);

    @LayoutRes
    protected abstract int getContentResource();


}
