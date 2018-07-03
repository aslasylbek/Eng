package com.example.aslan.mvpmindorkssample.ui.splash;

import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;

/**
 * Created by aslan on 17.05.2018.
 */

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    public SplashPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void decideNextActivty() {
        if (getDataManager().getLoggedMode()){
            getMvpView().openMainActivity();
        }
        else
            getMvpView().openLoginActivity();
    }

}
