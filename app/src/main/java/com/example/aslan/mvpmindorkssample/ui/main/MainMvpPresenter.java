package com.example.aslan.mvpmindorkssample.ui.main;

import com.example.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;

/**
 * Created by aslan on 17.05.2018.
 */

public interface MainMvpPresenter<V extends MvpView> extends MvpPresenter<V> {

    String getEmailId();

    void setUserLoggedOut();

}
