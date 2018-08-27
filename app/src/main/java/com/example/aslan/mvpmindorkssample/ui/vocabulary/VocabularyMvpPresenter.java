package com.example.aslan.mvpmindorkssample.ui.vocabulary;

import com.example.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.google.gson.JsonObject;

public interface VocabularyMvpPresenter<V extends VocabularyMvpView> extends MvpPresenter<V>{

    void requestForWord(String topicId);

    void requestSendResult(JsonObject jsonObject);
}
