package com.example.aslan.mvpmindorkssample.ui.main;

import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.ui.main.MainMvpPresenter;
import com.example.aslan.mvpmindorkssample.ui.main.MainMvpView;

/**
 * Created by aslan on 17.05.2018.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    public MainPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public String getEmailId() {
        return getDataManager().getBarcode();
    }

    @Override
    public void setUserLoggedOut() {
        getDataManager().clear();
        getMvpView().openSlashActivity();
    }
}
