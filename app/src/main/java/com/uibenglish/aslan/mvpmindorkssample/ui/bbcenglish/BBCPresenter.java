package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish;

import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCCategories;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;

import java.util.List;

public class BBCPresenter<V extends BBCContract.BBCMvpView> extends BasePresenter<V> implements BBCContract.BBCMvpPresenter<V> {

    public BBCPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void requestForBCCCategories() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().getBBCCategories(new DataManager.GetBBCCategories() {
                @Override
                public void onSuccess(List<BBCCategories> bbcCategories) {
                    Log.e("BBC", "onSuccess: "+bbcCategories );
                    getMvpView().setTabbarTitles(bbcCategories);
                    getMvpView().hideLoading();
                }

                @Override
                public void onError(Throwable t) {
                    getMvpView().hideLoading();
                }
            });
        } else getMvpView().noInternetConnection();
    }
}
