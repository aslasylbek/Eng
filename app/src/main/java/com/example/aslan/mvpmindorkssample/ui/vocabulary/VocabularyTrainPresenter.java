package com.example.aslan.mvpmindorkssample.ui.vocabulary;

import android.util.Log;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.VocabularyMvpPresenter;
import com.example.aslan.mvpmindorkssample.ui.vocabulary.VocabularyMvpView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
    public void requestSendResult(JsonObject object, int result) {
        object.addProperty("result", result);
        object.addProperty("datas", new Gson().toJson(datas));
        getMvpView().showLoading();
        getDataManager().postChapterResult(object, new DataManager.GetVoidPostCallback() {
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

    @Override
    public void addToJson(String wordId, int responses) {
        try {
            object = new JsonObject();
            object.addProperty("wordId", wordId);
            object.addProperty("isCorrect", responses);
            datas.add(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
