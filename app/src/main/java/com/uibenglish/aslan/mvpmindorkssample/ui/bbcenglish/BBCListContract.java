package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish;

import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLessonsList;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;

import java.util.List;

public interface BBCListContract {

    interface BBCListMvpView extends MvpView{
        void reloadListData(List<BBCLessonsList.Lesson> lessonList);
    }
    interface BBCListMvpPresenter<V extends BBCListMvpView> extends MvpPresenter<V> {
        void requestLessonsById(int category_id);
    }
}
