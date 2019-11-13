package com.uibenglish.aslan.mvpmindorkssample.ui.main.syllabus;

import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.content.EngInformationResponse;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.English;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Topic;

import java.util.List;

public class SyllabusPresenter<V extends SyllabusMvpView> extends BasePresenter<V> implements SyllabusMvpPresenter<V> {


    public SyllabusPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void getTopicsInformation() {
        if (isAttached()) {
            if (getMvpView().isNetworkConnected()) {
                getMvpView().showLoading();
                getDataManager().requestForEnglishInformation(new DataManager.GetEnglishInformation() {
                    @Override
                    public void onSuccess(EngInformationResponse response) {
                        if (response.getSuccess() == 1 && !response.getEnglish().isEmpty()) {
                            English english = response.getEnglish().get(0);
                            List<Topic> topicList = english.getTopics();
                            if (isAttached()) {
                                getMvpView().setTopicsData(topicList, response.getInfo().get(0).getCurrentTime());
                            }
                            getDataManager().clearAllDatabase();
                            for (int i = 0; i < english.getTopics().size(); i++) {
                                Topic topic = english.getTopics().get(i);
                                if (!topic.getWords().isEmpty()) {
                                    topic.setHaveWords(true);
                                    Log.d("AAA", "onSuccess: Have Words");
                                }
                                if (!topic.getGrammar().get(0).getConstructor().isEmpty() || !topic.getGrammar().get(0).getMissword().isEmpty()) {
                                    topic.setHaveGrammar(true);
                                    Log.d("AAA", "onSuccess: grammar");
                                }
                                if (!topic.getListening().isEmpty()) {
                                    topic.setHaveListening(true);
                                    Log.d("AAA", "onSuccess: listening");
                                }
                                if (!topic.getReading().isEmpty()) {
                                    topic.setHaveReading(true);
                                    Log.d("AAA", "onSuccess: reading");
                                }
                                getDataManager().saveTopics(topic);

                                for (int j = 0; j < topic.getWords().size(); j++) {
                                    getDataManager().saveWords(topic.getWords().get(j), topic.getTopicId());
                                }

                                for (int j = 0; j < topic.getReading().size(); j++) {
                                    getDataManager().saveReading(topic.getReading().get(j), topic.getTopicId());
                                }
                            }
                        } else {
                            getMvpView().setOnErrorMessage();
                            getMvpView().showToastMessage(R.string.no_english);
                        }
                        if (isAttached())
                            getMvpView().hideLoading();
                    }

                    @Override
                    public void onError() {
                        getMvpView().hideLoading();
                        getMvpView().setOnErrorMessage();
                        getMvpView().showToastMessage(R.string.get_wrong);
                    }
                });
            }else getMvpView().noInternetConnection();
        }

    }
}
