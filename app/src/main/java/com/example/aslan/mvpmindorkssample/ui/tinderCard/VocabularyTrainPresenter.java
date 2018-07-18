package com.example.aslan.mvpmindorkssample.ui.tinderCard;

import android.util.Log;

import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;

public class VocabularyTrainPresenter<V extends VocabularyMvpView> extends BasePresenter<V> implements VocabularyMvpPresenter<V> {

    public VocabularyTrainPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void requestForWord(String word) {
        if (word!=null&&!word.isEmpty()) {
            getMvpView().showLoading();
            getDataManager().requestForWordTranslate(word, new DataManager.GetWordTranslation() {
                @Override
                public void onSuccess(TranslationResponse response) {
                    getMvpView().setData(response);
                    getMvpView().hideLoading();
                }

                @Override
                public void onError() {
                    Log.d("AAA", "onError: ");
                    getMvpView().hideLoading();

                }
            });
            //getMvpView().hideLoading();
        }
    }
}
