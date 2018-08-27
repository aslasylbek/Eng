package com.example.aslan.mvpmindorkssample.ui.grammar;

import com.example.aslan.mvpmindorkssample.BuildConfig;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.ui.main.content.Grammar;

import java.util.List;

import retrofit2.Response;

public class GrammarPresenter<V extends GrammarContract.GrammarMvpView> extends BasePresenter<V> implements GrammarContract.GrammarMvpPresenter<V> {

    public GrammarPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void getGrammarLocalData(String topic_id) {
        getMvpView().showLoading();
        List<Grammar> grammarList = getDataManager().getGrammarByTopicId(topic_id);
        getMvpView().setNewDataFromRoom(grammarList);
        getMvpView().hideLoading();
    }

    @Override
    public void sendGrammarResult(String ex_id, String topicId, int result) {
        getMvpView().showLoading();
        getDataManager().requestPostTaskResult(ex_id, Integer.parseInt(topicId), result, new DataManager.GetVoidPostCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().hideLoading();
            }
        });

        getMvpView().addFinishFragment();


    }
}
