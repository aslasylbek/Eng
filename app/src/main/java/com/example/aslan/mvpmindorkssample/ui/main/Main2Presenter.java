package com.example.aslan.mvpmindorkssample.ui.main;

import android.util.Log;

import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.EngInformationResponse;
import com.example.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.ui.main.content.Topic;

public class Main2Presenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {


    public Main2Presenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void setUserLogOut() {
        getMvpView().showLoading();
        if (!isAttached()){
            return;
        }
        getDataManager().setLoggedMode(false);
        getMvpView().openSlashActivity();
        getMvpView().hideLoading();
    }

    @Override
    public void requestForStudentDiscipline() {
        getMvpView().showLoading();
        getMvpView().setNavigationView();
        getDataManager().requestForEnglishInformation(getDataManager().getPrefUserid(), new DataManager.GetEnglishInformation() {
            @Override
            public void onSuccess(EngInformationResponse response) {
                if (response.getSuccess()==1){
                    getMvpView().setHeaderView(response.getInfo().get(0));
                    //check if
                    if (!response.getEnglish().isEmpty()) {
                        Log.d("AAA", "onSuccess: " + response.getEnglish().get(0).getTopics().get(0).getTopicId());
                        getMvpView().setHolderData(response.getEnglish().get(0).getTopics());


                        getDataManager().clearAllDatabase();
                        for (int i = 0; i < response.getEnglish().get(0).getTopics().size(); i++) {
                            Topic topic = response.getEnglish().get(0).getTopics().get(i);
                            if (!topic.getWords().isEmpty()) {
                                topic.setHaveWords(true);
                                Log.d("AAA", "onSuccess: Have Words");
                            }
                            if (!topic.getGrammar().isEmpty()) {
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

                            for (int j = 0; j < response.getEnglish().get(0).getTopics().get(i).getWords().size(); j++) {
                                getDataManager().saveWords(response.getEnglish().get(0).getTopics().get(i).getWords().get(j),
                                        response.getEnglish().get(0).getTopics().get(i).getTopicId());
                            }

                            for (int j = 0; j < response.getEnglish().get(0).getTopics().get(i).getReading().size(); j++) {
                                getDataManager().saveReading(response.getEnglish().get(0).getTopics().get(i).getReading().get(j),
                                        response.getEnglish().get(0).getTopics().get(i).getTopicId());
                            }

                            for (int j = 0; j < response.getEnglish().get(0).getTopics().get(i).getGrammar().size(); j++) {
                                getDataManager().saveGrammar(response.getEnglish().get(0).getTopics().get(i).getGrammar().get(j),
                                        response.getEnglish().get(0).getTopics().get(i).getTopicId());
                            }


                        }
                    }
                    else {
                        //You dont have any english subject
                    }
                }
                else {

                }
                getMvpView().hideLoading();
            }

            @Override
            public void onError() {
                getMvpView().hideLoading();
            }
        });
    }

    @Override
    public void requestForHeaderView() {
        //SomeRequest
    }
}
