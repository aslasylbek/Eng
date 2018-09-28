package com.uibenglish.aslan.mvpmindorkssample.ui.word_wallet;

import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.models.WordCollection;
import com.uibenglish.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;

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
                if (wordCollectionList.size()>0)
                    getMvpView().setWordsCollection(wordCollectionList);
                getMvpView().hideLoading();
            }

                @Override
                public void onError (Throwable t){
                    getMvpView().showToastMessage(R.string.get_wrong);
                    getMvpView().hideLoading();
                }
            });
        }

        @Override
        public void addWordAsKnown (String word_id){
            getMvpView().showLoading();
            getDataManager().postToChangeWordAsKnown(word_id, new DataManager.GetVoidPostCallback() {
                @Override
                public void onSuccess(Response<PostDataResponse> response) {
                    getMvpView().showSnackbar();
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
