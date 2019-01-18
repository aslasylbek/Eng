package com.uibenglish.aslan.mvpmindorkssample.ui.listening;

import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;

public interface ListeningTaskContract {

    interface ListeningTaskMvpView extends MvpView{
        void updateUI();
    }

    interface ListeningTaskMvpPresenter<V extends ListeningTaskMvpView> extends MvpPresenter<V>{
        void postListeningResult(String topicId, int result, long startTime);
    }
}
