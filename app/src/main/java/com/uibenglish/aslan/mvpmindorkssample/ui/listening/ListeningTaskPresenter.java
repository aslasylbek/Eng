package com.uibenglish.aslan.mvpmindorkssample.ui.listening;

import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;

import retrofit2.Response;

public class ListeningTaskPresenter<V extends ListeningTaskContract.ListeningTaskMvpView> extends BasePresenter<V> implements ListeningTaskContract.ListeningTaskMvpPresenter<V> {

    public ListeningTaskPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void postListeningResult(String topicId, int result, long startTime) {
        if (!isAttached()){
            return;
        }
        getMvpView().showLoading();
        getDataManager().postListeningResult(topicId, result, startTime, new DataManager.GetVoidPostCallback() {
            @Override
            public void onSuccess(Response<PostDataResponse> response) {
                getMvpView().updateUI();
                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().hideLoading();
            }
        });

    }
}
