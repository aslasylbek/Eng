package com.uibenglish.aslan.mvpmindorkssample.ui.word_wallet;

import com.uibenglish.aslan.mvpmindorkssample.data.models.WordCollection;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;

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
