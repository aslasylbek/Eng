package com.example.aslan.mvpmindorkssample.ui.word_wallet;

import android.util.Log;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.example.aslan.mvpmindorkssample.data.models.WordCollection;
import com.example.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class WordBookPresenter<V extends WordBookContract.WordBookMvpView> extends BasePresenter<V> implements WordBookContract.WordBookMvpPresenter<V> {

    public WordBookPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void requestWordsCollection() {
        getMvpView().showLoading();
        getDataManager().getStudentDictionary(new DataManager.GetWordCollectionCallback() {
            @Override
            public void onSuccess(List<WordCollection> wordCollectionList) {
                List<WordCollection> unknownWordsList = new ArrayList<>();
                for (int i=0; i<wordCollectionList.size(); i++){
                    if (wordCollectionList.get(i).getRating().equals("0")){
                        unknownWordsList.add(wordCollectionList.get(i));
                    }
                }
                getMvpView().setWordsCollection(unknownWordsList);
                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().showToastMessage(R.string.get_wrong);
                getMvpView().hideLoading();
            }
        });
    }

    @Override
    public void addWordAsKnown(String word_id) {
        getMvpView().showLoading();
        getDataManager().postToChangeWordAsKnown(word_id, new DataManager.GetVoidPostCallback() {
            @Override
            public void onSuccess(Response<PostDataResponse> response) {

                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().showToastMessage(R.string.get_wrong);
                getMvpView().hideLoading();
            }
        });
    }
}
