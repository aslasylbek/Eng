package com.example.aslan.mvpmindorkssample.ui.reading;

import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.Translate;
import com.example.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class ReaderPresenter<V extends ReaderMvpContract.ReaderMvpView> extends BasePresenter<V> implements ReaderMvpContract.ReaderMvpPresenter<V>{

    public ReaderPresenter(DataManager mDataManager) {
        super(mDataManager);

    }


    //Now it gets all texts but write case for one
    @Override
    public void requestForLocalReadingText(String topicId) {
        getMvpView().showLoading();
        getMvpView().setTextFromDb(getDataManager().getTextByTopicId(topicId).get(0).getReading());
        getMvpView().hideLoading();
    }

    @Override
    public void getWordTranslate(String word) {
        getMvpView().showLoading();
        getDataManager().requestForWordTranslation(word, new DataManager.GetWordTranslation() {
            @Override
            public void onSuccess(List<Translate> response) {
                String[] translates = new String[response.size()];
                for (int i=0; i<response.size(); i++){
                    System.out.println(response.get(i).getValue());
                    translates[i] = response.get(i).getValue();
                }
                getMvpView().setTranslate(translates);
                getMvpView().hideLoading();
            }

            @Override
            public void onError() {
                getMvpView().hideLoading();
            }
        });
    }


}
