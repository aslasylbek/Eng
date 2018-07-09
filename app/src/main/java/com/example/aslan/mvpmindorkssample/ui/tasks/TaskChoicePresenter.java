package com.example.aslan.mvpmindorkssample.ui.tasks;

import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.ui.main.expandable.LessonTopicItem;

public class TaskChoicePresenter<V extends TaskChoiceMvpView>extends BasePresenter<V> implements TaskChoiceMvpPresenter<V> {

    public TaskChoicePresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void initPresenter() {
        LessonTopicItem topicItem = getMvpView().getTopicsGeneral();
        getMvpView().showTopicDetails(topicItem);

    }

    @Override
    public void requestForTasks() {
        //getMvpView().showLoading();
        getMvpView().setViewPagerData();
        //getMvpView().hideLoading();
    }

}
