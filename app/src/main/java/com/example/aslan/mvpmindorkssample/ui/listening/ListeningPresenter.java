package com.example.aslan.mvpmindorkssample.ui.listening;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.example.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.ui.main.content.Listening;

import java.util.List;

import retrofit2.Response;

public class ListeningPresenter<V extends ListeningContract.ListeningMvpView> extends BasePresenter<V> implements ListeningContract.ListeningMvpPresenter<V> {

    public ListeningPresenter(DataManager mDataManager) {
        super(mDataManager);
    }


    @Override
    public void requestForListeningCollection(String topic_id) {
        getMvpView().showLoading();
        getDataManager().getListeningArray(topic_id, new DataManager.GetListeningListCallback() {
            @Override
            public void onSuccess(List<Listening> readingList) {
                getMvpView().spreadListeningCollection(readingList);
                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().hideLoading();
                getMvpView().showToastMessage(R.string.get_wrong);
            }
        });
    }

    @Override
    public void setListeningResult(String topic_id, int result_ans, long start_time) {
        final int res = result_ans;
        getMvpView().showLoading();
        getDataManager().postListeningResult(topic_id, result_ans, start_time, new DataManager.GetVoidPostCallback() {
            @Override
            public void onSuccess(Response<PostDataResponse> response) {
                if (response.body().getSuccess()==1) {
                    getMvpView().addFinishFragment(res);
                }
                else getMvpView().showToastMessage(R.string.get_wrong);
                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().hideLoading();
                getMvpView().showToastMessage(R.string.get_wrong);
            }
        });
    }
}
