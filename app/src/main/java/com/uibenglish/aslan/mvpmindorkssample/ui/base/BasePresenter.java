package com.uibenglish.aslan.mvpmindorkssample.ui.base;

import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;

/**
 * Created by aslan on 17.05.2018.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;
    private DataManager dataManager;


    public BasePresenter(DataManager mDataManager) {
        dataManager = mDataManager;
    }


    @Override
    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        if (mMvpView!=null)
            mMvpView = null;
    }

    @Override
    public boolean isAttached() {
        return mMvpView!=null;
    }

    public V getMvpView(){
        return mMvpView;
    }

    public DataManager getDataManager(){
        return dataManager;
    }
}
