package com.uibenglish.aslan.mvpmindorkssample.ui.reading;

import android.util.Log;
import android.widget.Toast;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.content.Translate;
import com.uibenglish.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Reading;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ReaderPresenter<V extends ReaderMvpContract.ReaderMvpView> extends BasePresenter<V> implements ReaderMvpContract.ReaderMvpPresenter<V>{

    public ReaderPresenter(DataManager mDataManager) {
        super(mDataManager);

    }

    //Now it gets all texts but write case for one
    @Override
    public void requestForLocalReadingText(String topicId) {
        getMvpView().showLoading();
        getDataManager().getReadingArray(topicId, new DataManager.GetReadingListCallback() {
            @Override
            public void onSuccess(List<Reading> readingList) {
                getMvpView().spreadTextToFragments(readingList);
                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().hideLoading();
                getMvpView().showToastMessage(R.string.get_wrong);
            }
        });
        //getMvpView().spreadTextToFragments(getDataManager().getTextByTopicId(topicId).get(0).getReading());

    }

    @Override
    public void postResultToServer(String topic_id, int result_tf, int result_ans, long startTime) {
        final int totalResult = (result_ans+result_tf)/2;
        getMvpView().showLoading();
        getDataManager()
                .postReadingResult(topic_id, result_tf, result_ans, startTime, new DataManager.GetVoidPostCallback() {
            @Override
            public void onSuccess(Response<PostDataResponse> response) {
                if (response.body().getSuccess()==1) {
                    getMvpView().addFinishFragment(totalResult);
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

    @Override
    public void getWordTranslate(String word) {
        getMvpView().showLoading();
        getDataManager().requestForWordTranslation(word, new DataManager.GetWordTranslation() {
            @Override
            public void onSuccess(TranslationResponse response) {
                List<Translate> translateList = response.getTranslate();
                String[] translates = new String[translateList.size()];
                for (int i=0; i<translateList.size(); i++){
                    System.out.println(translateList.get(i).getValue());
                    translates[i] = translateList.get(i).getValue();
                }
                getMvpView().setTranslate(translates[0]);
                getMvpView().hideLoading();
            }

            @Override
            public void onError() {
                getMvpView().hideLoading();
            }
        });
    }

    @Override
    public void addToDictionary(final String word) {
        if (word.equals("")||word.isEmpty()) {
            return;
        }

        getMvpView().showLoading();
        getDataManager().postUnknownWord(word, new DataManager.GetVoidPostCallback() {
            @Override
            public void onSuccess(Response<PostDataResponse> response) {
                getMvpView().hideLoading();
                getMvpView().showToastMessage(R.string.added_to_word_book);
            }
            @Override
            public void onError(Throwable t) {
                getMvpView().hideLoading();
                getMvpView().showToastMessage(R.string.get_wrong);
            }
        });
    }
}
