package com.uibenglish.aslan.mvpmindorkssample.ui.user_result;

import com.uibenglish.aslan.mvpmindorkssample.R;
import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.ResultStudentTasks;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BaseFragment;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;

public class UserResultPresenter<V extends UserResultContract.UserResultMvpView> extends BasePresenter<V> implements UserResultContract.UserResultMvpPresenter<V> {

    public UserResultPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void requestToUserResult() {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().getUserResult(new DataManager.GetUserResultCallback() {
                @Override
                public void onSuccess(ResultStudentTasks resultStudentTasks) {
                    if (resultStudentTasks.getTopics() != null) {
                        getMvpView().spreadUserResults(resultStudentTasks.getTopics());
                    }
                    getMvpView().hideLoading();
                }

                @Override
                public void onError(Throwable t) {
                    getMvpView().showToastMessage(R.string.get_wrong);
                    getMvpView().hideLoading();
                }
            });
        }else getMvpView().noInternetConnection();
    }
}
