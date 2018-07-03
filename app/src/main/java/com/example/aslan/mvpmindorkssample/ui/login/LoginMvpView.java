package com.example.aslan.mvpmindorkssample.ui.login;

import com.example.aslan.mvpmindorkssample.ui.base.MvpView;

/**
 * Created by aslan on 17.05.2018.
 */

public interface LoginMvpView extends MvpView {

    void openMainActivity();
    void onLoginButtonClick();
    User getUser();
    void showToast(int resId);

}
