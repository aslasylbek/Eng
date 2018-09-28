package com.uibenglish.aslan.mvpmindorkssample.ui.tasks;

import com.uibenglish.aslan.mvpmindorkssample.general.LoadingView;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.expandable.LessonChildItem;

public interface TaskChoiceMvpView extends MvpView, LoadingView{

    void setViewPagerData(LessonChildItem lessonChildItem);

}
