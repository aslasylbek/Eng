package com.uibenglish.aslan.mvpmindorkssample.ui.main;

import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.content.EngInformationResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.models.PostDataResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.English;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Topic;

import retrofit2.Response;

public class Main2Presenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {


    public Main2Presenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void setUserLogOut() {
        if (!isAttached()){
            return;
        }
        getMvpView().showLoading();
        getDataManager().putUserId("");
        getDataManager().sendDeviceToken(new DataManager.GetVoidPostCallback() {
            @Override
            public void onSuccess(Response<PostDataResponse> response) {
                getDataManager().setLoggedMode(false);
                getDataManager().putProgram(null);
                getDataManager().putName(null);
                getDataManager().putGroup(null);
                getMvpView().openSlashActivity();
                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getDataManager().setLoggedMode(false);
                getDataManager().putProgram(null);
                getDataManager().putName(null);
                getDataManager().putGroup(null);
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
                    //getMvpView().setHeaderView(response.getInfo().get(0));
                    if (!response.getInfo().isEmpty()){
                        getDataManager().putGroup(response.getInfo().get(0).getGroup());
                        getDataManager().putName(response.getInfo().get(0).getFio());
                        getDataManager().putProgram(response.getInfo().get(0).getProgram());
                        getMvpView().setHeaderView(getDataManager().getName(), getDataManager().getGroup(), getDataManager().getProgram());
                    }
                    //check if
                    if (!response.getEnglish().isEmpty()) {
                        English english = response.getEnglish().get(0);
                        getDataManager().putCourseId(english.getCourseId());
                    }
                }
                else {
                    getMvpView().showToastMessage(R.string.get_wrong);
                    getMvpView().setHeaderView(getDataManager().getName(), getDataManager().getGroup(), getDataManager().getProgram());
                }
                getMvpView().hideLoading();
            }

            @Override
            public void onError() {
                getMvpView().showToastMessage(R.string.get_wrong);
                getMvpView().setHeaderView(getDataManager().getName(), getDataManager().getGroup(), getDataManager().getProgram());
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
