package com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary;

import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.google.gson.JsonObject;

import java.util.Map;

public interface VocabularyMvpPresenter<V extends VocabularyMvpView> extends MvpPresenter<V>{

    void requestForWord(String topicId);

    void requestSendResult(Map<String, Integer> jsonObject, int res, String topic_id, String chapter, long startTime);

}
