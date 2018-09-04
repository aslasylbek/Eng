package com.example.aslan.mvpmindorkssample.ui.user_result;

import com.example.aslan.mvpmindorkssample.data.models.ResultTopic;
import com.example.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;

import java.util.List;

public interface UserResultContract {

    interface UserResultMvpView extends MvpView{
        void spreadUserResults(List<ResultTopic> resultTopicList);
    }

    interface UserResultMvpPresenter<V extends UserResultMvpView> extends MvpPresenter<V>{
        void requestToUserResult();
    }
}
