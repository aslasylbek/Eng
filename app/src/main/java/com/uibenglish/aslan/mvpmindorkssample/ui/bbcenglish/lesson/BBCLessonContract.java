package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish.lesson;

import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLesson;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;

public interface BBCLessonContract {
    interface BBCLessonMvpView extends MvpView{
        void updateUI(BBCLesson bbcLesson);
    }

    interface BBCLessonMvpPresenter<V extends BBCLessonMvpView> extends MvpPresenter<V>{
        void requestBBCLessonDataById(String lessonId);

    }
}
