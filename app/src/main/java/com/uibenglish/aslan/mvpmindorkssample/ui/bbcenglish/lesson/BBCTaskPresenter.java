package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish.lesson;

import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;

import java.util.Map;

import retrofit2.Response;

public class BBCTaskPresenter<V extends BBCTaskContract.BBCTaskMvpView> extends BasePresenter<V> implements BBCTaskContract.BBCTaskMvpPresenter<V> {

    public BBCTaskPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void sendBBCQuestions(String lesson_id, int taskId, long startTime, Map<String, String> transcript) {
        if(isAttached()){
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                getDataManager().postBBCQuestions(lesson_id, taskId, startTime, transcript, new DataManager.GetVoidPostCallback() {
                    @Override
                    public void onSuccess(Response<PostDataResponse> response) {
                        if (response.isSuccessful()) {
                            response.body().getMessage();
                        }
                        getMvpView().updateUI();
                        getMvpView().hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        getMvpView().updateUI();
                        getMvpView().hideLoading();
                    }
                });
            }else getMvpView().noInternetConnection();
        }
    }
}
