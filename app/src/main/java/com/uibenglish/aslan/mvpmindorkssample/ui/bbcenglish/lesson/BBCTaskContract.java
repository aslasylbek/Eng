package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish.lesson;

import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;

import java.util.Map;

public interface BBCTaskContract {

    interface BBCTaskMvpView extends MvpView{
        void updateUI();
    }

    interface BBCTaskMvpPresenter<V extends BBCTaskMvpView> extends MvpPresenter<V>{
        void sendBBCQuestions(String lesson_id, int taskId, Map<String, String> answers);
    }

}
