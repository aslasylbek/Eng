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
import com.uibenglish.aslan.mvpmindorkssample.utils.NetworkUtils;

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

        if (getMvpView().isNetworkConnected()) {
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
        } else getMvpView().noInternetConnection();
    }

    @Override
    public void requestForStudentDiscipline() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().requestForEnglishInformation(new DataManager.GetEnglishInformation() {
                @Override
                public void onSuccess(EngInformationResponse response) {
                    if (response.getSuccess() == 1) {
                        if (!response.getInfo().isEmpty()) {
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
                    } else {
                        getMvpView().showToastMessage(R.string.error_profile_size);
                        Log.e("AA", "onSuccess: Error in success");
                    }
                    getMvpView().hideLoading();
                }

                @Override
                public void onError() {
                    getMvpView().showToastMessage(R.string.error_profile);
                    getMvpView().hideLoading();
                }
            });
        }else getMvpView().noInternetConnection();
    }

    @Override
    public void sendUserMessage(String message) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().postUserFeedback(message, new DataManager.GetVoidPostCallback() {
                @Override
                public void onSuccess(Response<PostDataResponse> response) {
                    if (response.isSuccessful())
                        if (response.body() != null) {
                            getMvpView().showSnackbar(response.body().getMessage());
                        }
                    getMvpView().hideLoading();
                }

                @Override
                public void onError(Throwable t) {
                    getMvpView().hideLoading();
                }
            });
        }
        else {
            getMvpView().noInternetConnection();
        }
    }

    @Override
    public void sendDeviceToken() {
        Log.e("Main", "sendDeviceToken: " );
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().sendDeviceToken(new DataManager.GetVoidPostCallback() {
                @Override
                public void onSuccess(Response<PostDataResponse> response) {
                    getMvpView().startDeviceTokenRouting();
                    getMvpView().hideLoading();
                }

                @Override
                public void onError(Throwable t) {
                    getMvpView().hideLoading();
                }
            });
        }else getMvpView().noInternetConnection();
    }
}
