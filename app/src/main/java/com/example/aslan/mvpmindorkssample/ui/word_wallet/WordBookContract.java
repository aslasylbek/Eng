package com.example.aslan.mvpmindorkssample.ui.word_wallet;

import com.example.aslan.mvpmindorkssample.data.models.WordCollection;
import com.example.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;

import java.util.List;

public interface WordBookContract {

    interface WordBookMvpView extends MvpView{
        void setWordsCollection(List<WordCollection> wordsCollection);

        void showSnackbar();
    }

    interface WordBookMvpPresenter<V extends WordBookContract.WordBookMvpView> extends MvpPresenter<V>{

        void requestWordsCollection();
        void addWordAsKnown(String word_id);
    }

}
