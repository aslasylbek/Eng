package com.example.aslan.mvpmindorkssample.ui.login;

import android.util.Log;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.LoginResponse;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.utils.CommonUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by aslan on 17.05.2018.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    public LoginPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void startLogin() {
        User user = getMvpView().getUser();
        if(!CommonUtils.isBarcodeValid(user.getBarcode())){
            getMvpView().showToast(R.string.toast_barcode_correct);
            return;
        }
        if (user.getPassword().isEmpty()){
            getMvpView().showToast(R.string.toast_password_cor);
            return;
        }
        if (user.isSaveBarcode()){
            getDataManager().putBarcode(user.getBarcode());
        }
        if (user.isSavePassword()){
            getDataManager().putPassword(user.getPassword());
        }


        getMvpView().showLoading();
        getDataManager()
                .sendForToken(user.getBarcode(), user.getPassword(), new DataManager.GetTokenCallbacks() {
            @Override
            public void onSuccess(LoginResponse response) {
                if (response.getSuccess()==1){
                    Log.d("MC", response.getMessage()+"");
                    getDataManager().setLoggedMode(true);
                    getDataManager().putToken(response.getToken());
                    getMvpView().openMainActivity();
                }
                else {
                    getMvpView().showToast(R.string.toast_barcode_cor);
                    Log.d("MC", response.getMessage()+"ccc");
                }

            }
            @Override
            public void onError() {
                Log.d("MC", "BUG");
            }
        });
        //getMvpView().hideLoading();


    }
}
