package com.uibenglish.aslan.mvpmindorkssample.ui.user_result;

import com.uibenglish.aslan.mvpmindorkssample.data.models.ResultTopic;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;

import java.util.List;

public interface UserResultContract {

    interface UserResultMvpView extends MvpView{
        void spreadUserResults(List<ResultTopic> resultTopicList);
    }

    interface UserResultMvpPresenter<V extends UserResultMvpView> extends MvpPresenter<V>{
        void requestToUserResult();
    }
}
