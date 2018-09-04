package com.example.aslan.mvpmindorkssample.ui.user_result;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aslan.mvpmindorkssample.MvpApp;
import com.example.aslan.mvpmindorkssample.R;
import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.data.models.ResultTopic;
import com.example.aslan.mvpmindorkssample.ui.base.BaseFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserResultFragment extends BaseFragment implements UserResultContract.UserResultMvpView {

    private static final String TAG = "UserResultFragment";
    private UserResultPresenter resultPresenter;


    @Override
    protected void init(@Nullable Bundle bundle) {

        DataManager manager = ((MvpApp)getActivity().getApplicationContext()).getDataManager();
        resultPresenter = new UserResultPresenter(manager);
        resultPresenter.attachView(this);
        resultPresenter.requestToUserResult();
    }

    @Override
    protected int getContentResource() {
        return R.layout.fragment_user_result;
    }

    @Override
    public void spreadUserResults(List<ResultTopic> resultTopicList) {
        Log.d(TAG, "spreadUserResults: ");
    }

    @Override
    public void onDestroyView() {
        resultPresenter.detachView();
        super.onDestroyView();
    }
}
