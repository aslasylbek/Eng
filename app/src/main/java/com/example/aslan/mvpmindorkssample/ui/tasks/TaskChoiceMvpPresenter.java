package com.example.aslan.mvpmindorkssample.ui.tasks;

import com.example.aslan.mvpmindorkssample.ui.base.MvpPresenter;

public interface TaskChoiceMvpPresenter<V extends TaskChoiceMvpView> extends MvpPresenter<V> {

    void requestForTasks();
    void initPresenter();
}
