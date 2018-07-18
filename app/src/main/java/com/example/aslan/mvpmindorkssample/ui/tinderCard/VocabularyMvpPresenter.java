package com.example.aslan.mvpmindorkssample.ui.tinderCard;

import com.example.aslan.mvpmindorkssample.ui.base.MvpPresenter;

public interface VocabularyMvpPresenter<V extends VocabularyMvpView> extends MvpPresenter<V>{

    void requestForWord(String word);
}
