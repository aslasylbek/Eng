package com.example.aslan.mvpmindorkssample.ui.tasks;

import com.example.aslan.mvpmindorkssample.general.LoadingView;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;
import com.example.aslan.mvpmindorkssample.ui.main.expandable.LessonChildItem;

public interface TaskChoiceMvpView extends MvpView, LoadingView{

    void setViewPagerData(LessonChildItem lessonChildItem);

}
