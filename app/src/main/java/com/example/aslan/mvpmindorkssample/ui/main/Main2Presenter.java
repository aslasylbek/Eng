package com.example.aslan.mvpmindorkssample.ui.main;

import android.util.Log;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.content.EngInformationResponse;
import com.example.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.example.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.ui.main.content.English;
import com.example.aslan.mvpmindorkssample.ui.main.content.Topic;

import retrofit2.Response;

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
        getDataManager().putUserId("");
        getDataManager().sendDeviceToken(new DataManager.GetVoidPostCallback() {
            @Override
            public void onSuccess(Response<PostDataResponse> response) {
                getDataManager().setLoggedMode(false);
                getMvpView().openSlashActivity();
                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getDataManager().setLoggedMode(false);
                getMvpView().openSlashActivity();
                getMvpView().hideLoading();
            }
        });

    }

    @Override
    public void requestForStudentDiscipline() {
        getMvpView().showLoading();
        getMvpView().setNavigationView();
        getDataManager().requestForEnglishInformation(new DataManager.GetEnglishInformation() {
            @Override
            public void onSuccess(EngInformationResponse response) {
                if (response.getSuccess()==1){
                    getMvpView().setHeaderView(response.getInfo().get(0));
                    //check if
                    if (!response.getEnglish().isEmpty()) {
                        English english = response.getEnglish().get(0);


                        //getMvpView().setHolderData(english.getTopics());
                        getDataManager().putCourseId(english.getCourseId());
                        /*getDataManager().clearAllDatabase();

                        for (int i = 0; i < english.getTopics().size(); i++) {
                            Topic topic = english.getTopics().get(i);
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

                            for (int j = 0; j < topic.getWords().size(); j++) {
                                getDataManager().saveWords(topic.getWords().get(j), topic.getTopicId());
                            }

                            for (int j = 0; j < topic.getReading().size(); j++) {
                                getDataManager().saveReading(topic.getReading().get(j), topic.getTopicId());
                            }

                            for (int j = 0; j < topic.getGrammar().size(); j++) {
                                getDataManager().saveGrammar(topic.getGrammar().get(j), topic.getTopicId());
                            }
                        }*/
                    }
                    else {
                        getMvpView().showToastMessage(R.string.no_english);
                    }
                }
                else {
                    getMvpView().showToastMessage(R.string.get_wrong);
                }
                getMvpView().hideLoading();
            }

            @Override
            public void onError() {
                getMvpView().showToastMessage(R.string.get_wrong);
                getMvpView().hideLoading();
            }
        });
    }

    @Override
    public void requestForHeaderView() {
        //SomeRequest
    }

    @Override
    public void sendDeviceToken() {
        getMvpView().showLoading();
        getDataManager().sendDeviceToken(new DataManager.GetVoidPostCallback() {
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
