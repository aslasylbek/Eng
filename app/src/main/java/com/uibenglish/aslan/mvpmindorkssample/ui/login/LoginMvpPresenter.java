package com.uibenglish.aslan.mvpmindorkssample.ui.login;

import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;

/**
 * Created by aslan on 17.05.2018.
 */

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void startLogin();

    void setPrefsIfExist();

}
