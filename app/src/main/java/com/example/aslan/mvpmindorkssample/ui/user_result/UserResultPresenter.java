package com.example.aslan.mvpmindorkssample.ui.user_result;

import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.models.ResultStudentTasks;
import com.example.aslan.mvpmindorkssample.ui.base.BaseFragment;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;

public class UserResultPresenter<V extends UserResultContract.UserResultMvpView> extends BasePresenter<V> implements UserResultContract.UserResultMvpPresenter<V> {

    public UserResultPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void requestToUserResult() {
        getMvpView().showLoading();
        getDataManager().getUserResult(new DataManager.GetUserResultCallback() {
            @Override
            public void onSuccess(ResultStudentTasks resultStudentTasks) {
                getMvpView().spreadUserResults(resultStudentTasks.getTopics());
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
