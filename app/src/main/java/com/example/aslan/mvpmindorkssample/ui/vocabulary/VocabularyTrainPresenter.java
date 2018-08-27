package com.example.aslan.mvpmindorkssample.ui.vocabulary;

import android.util.Log;

import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.VocabularyMvpPresenter;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.VocabularyMvpView;
import com.google.gson.JsonObject;

import retrofit2.Response;

public class VocabularyTrainPresenter<V extends VocabularyMvpView> extends BasePresenter<V> implements VocabularyMvpPresenter<V> {

    public VocabularyTrainPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void requestForWord(String topicId) {
            getMvpView().showLoading();
            getMvpView().setData(getDataManager().getWordsByTopicId(topicId));
            getMvpView().hideLoading();
        }


    /*@Override
    public void requestSendResult(JsonObject object) {
        //ResultContent resultContent = getMvpView().sendExerciseData();
        getMvpView().showLoading();
        String user_id = getDataManager().getPrefUserid();
        int u = Integer.parseInt(user_id);
        int t = Integer.parseInt(resultContent.getTopic_id());
        getDataManager().postChapterResult(resultContent.getChapter(), u, t, resultContent.getResult());
        getMvpView().addFinishFragment();
        getMvpView().hideLoading();
    }*/

    @Override
    public void requestSendResult(JsonObject object) {
        //ResultContent resultContent = getMvpView().sendExerciseData();
        getMvpView().showLoading();
        String user_id = getDataManager().getPrefUserid();
        object.addProperty("userId", user_id);
        getDataManager().postChapterResult(object, new DataManager.GetVoidPostCallback() {
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
