package com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary;

import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary.VocabularyMvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary.VocabularyMvpView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Response;

public class VocabularyTrainPresenter<V extends VocabularyMvpView> extends BasePresenter<V> implements VocabularyMvpPresenter<V> {

    JsonObject object;
    JsonArray datas;

    public VocabularyTrainPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void requestForWord(String topicId) {
            getMvpView().showLoading();
            getMvpView().setData(getDataManager().getWordsByTopicId(topicId));
            getMvpView().hideLoading();
        }

    @Override
    public void requestSendResult(Map<String, Integer> object, int result, String topic_id, String chapter, long startTime) {

        getMvpView().showLoading();
        getDataManager().postChapterResult(object, result, topic_id, chapter, startTime,  new DataManager.GetVoidPostCallback() {
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
        getMvpView().addFinishFragment(result);

    }
}
