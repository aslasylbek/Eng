package com.example.aslan.mvpmindorkssample.ui.grammar;

import com.example.aslan.mvpmindorkssample.BuildConfig;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.example.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.ui.main.content.Grammar;

import java.util.List;

import retrofit2.Response;

public class GrammarPresenter<V extends GrammarContract.GrammarMvpView> extends BasePresenter<V> implements GrammarContract.GrammarMvpPresenter<V> {

    public GrammarPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    /*@Override
    public void getGrammarLocalData(String topic_id) {
        getMvpView().showLoading();
        List<Grammar> grammarList = getDataManager().getGrammarByTopicId(topic_id);
        if (grammarList!=null) {
            getMvpView().setNewDataFromRoom(grammarList);
        }
        getMvpView().hideLoading();
    }*/

    @Override
    public void requestGrammarCollection(String topic_id) {
        getMvpView().showLoading();
        getDataManager().getGrammarArray(topic_id, new DataManager.GetGrammarListCallback() {
            @Override
            public void onSuccess(List<Grammar> grammarList) {
                getMvpView().setNewDataFromRoom(grammarList);
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
    public void sendGrammarResult(String topicId, int result_ans, int result_cons, long startTime) {
        getMvpView().showLoading();
        getDataManager().postGrammarResult(topicId, result_ans, result_cons, startTime, new DataManager.GetVoidPostCallback() {
            @Override
            public void onSuccess(Response<PostDataResponse> response) {
                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().hideLoading();
                getMvpView().showToastMessage(R.string.get_wrong);
            }
        });
        int total = (result_ans+result_cons)/2;
        getMvpView().addFinishFragment(total);


    }
}
