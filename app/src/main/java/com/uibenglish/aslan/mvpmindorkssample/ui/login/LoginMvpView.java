package com.uibenglish.aslan.mvpmindorkssample.ui.login;

import com.uibenglish.aslan.mvpmindorkssample.general.LoadingView;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;

/**
 * Created by aslan on 17.05.2018.
 */

public interface LoginMvpView extends MvpView {

    void openMainActivity();
    void onLoginButtonClick();
    User getUser();
    void showToast(int resId);
    void wrongLoginOrPassword();

    void setLoginPassword(String barcode, String password);

}
