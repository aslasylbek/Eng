package com.uibenglish.aslan.mvpmindorkssample.ui.login;

import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.content.LoginResponse;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.uibenglish.aslan.mvpmindorkssample.utils.CommonUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by aslan on 17.05.2018.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    public LoginPresenter(DataManager mDataManager) {
        super(mDataManager);
    }


    @Override
    public void setPrefsIfExist() {
        getMvpView().showLoading();
        getMvpView().setLoginPassword(getDataManager().getBarcode(), getDataManager().getPrefPassword());
        getMvpView().hideLoading();
    }

    @Override
    public void startLogin() {
        User user = getMvpView().getUser();
        if(!CommonUtils.isBarcodeValid(user.getBarcode())){
            getMvpView().showToast(R.string.toast_barcode_correct);
            return;
        }
        else if (user.getPassword().isEmpty()){
            getMvpView().showToast(R.string.toast_password_cor);
            return;
        }
        else if (user.isSaveBarcode()){
            getDataManager().putBarcode(user.getBarcode());
        }
        else if (user.isSavePassword()){
            getDataManager().putBarcode(user.getBarcode());
            getDataManager().putPassword(user.getPassword());
        }
        else{
            getDataManager().clear();
        }
        getMvpView().showLoading();
        getDataManager()
                .sendForToken(user.getBarcode(), user.getPassword(), new DataManager.GetTokenCallbacks() {
            @Override
            public void onSuccess(LoginResponse response) {
                if (response.getSuccess()==1){
                    getDataManager().setLoggedMode(true);
                    getDataManager().putToken(response.getToken());
                    getDataManager().putUserId(response.getUserID());
                    getMvpView().openMainActivity();
                }
                else {
                    getMvpView().showToast(R.string.toast_barcode_cor);
                    getMvpView().wrongLoginOrPassword();
                }
                getMvpView().hideLoading();

            }
            @Override
            public void onError(Throwable t) {
                Log.e("jubak onError", t.toString());
                getMvpView().showToastMessage(R.string.get_wrong);
                getMvpView().hideLoading();
            }
        });


    }


}
