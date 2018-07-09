package com.example.aslan.mvpmindorkssample.ui.main;

import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.LoginResponse;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;

public class Main2Presenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {


    public Main2Presenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void setUserLogOut() {
        getMvpView().showLoading();
        if (!isAttached()){
            return;
        }
        getDataManager().setLoggedMode(false);
        getMvpView().openSlashActivity();
        getMvpView().hideLoading();
    }

    @Override
    public void requestForNavigationView() {
        getMvpView().showLoading();
        getDataManager().sendForToken("1620883", "10121993", new DataManager.GetTokenCallbacks() {
            @Override
            public void onSuccess(LoginResponse response) {

            }

            @Override
            public void onError() {

            }
        });
        //Some request
        getMvpView().setNavigationView();
        getMvpView().hideLoading();
    }

    @Override
    public void requestForHeaderView() {
        //SomeRequest
        getMvpView().setHeaderView();
    }
}
