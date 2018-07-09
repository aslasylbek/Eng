package com.example.aslan.mvpmindorkssample.ui.tasks;

import com.example.aslan.mvpmindorkssample.general.LoadingView;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;
import com.example.aslan.mvpmindorkssample.ui.main.expandable.LessonTopicItem;

public interface TaskChoiceMvpView extends MvpView, LoadingView{

    void setViewPagerData();

    void showTopicDetails(LessonTopicItem topicItem);

    LessonTopicItem getTopicsGeneral();

}
