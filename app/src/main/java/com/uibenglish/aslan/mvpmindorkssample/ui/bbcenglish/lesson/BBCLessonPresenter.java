package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish.lesson;

import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLesson;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;

public class BBCLessonPresenter<V extends BBCLessonContract.BBCLessonMvpView> extends BasePresenter<V> implements BBCLessonContract.BBCLessonMvpPresenter<V> {

    public BBCLessonPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void requestBBCLessonDataById(String lessonId) {
        getMvpView().showLoading();
        getDataManager().getBBCLesson(lessonId, new DataManager.GetBBCLesson() {
            @Override
            public void onSuccess(BBCLesson bbcLessons) {
                getMvpView().updateUI(bbcLessons);
                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().hideLoading();
            }
        });
    }
}
