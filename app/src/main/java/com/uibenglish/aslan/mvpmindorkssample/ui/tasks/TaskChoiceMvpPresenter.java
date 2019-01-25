package com.uibenglish.aslan.mvpmindorkssample.ui.tasks;

import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;

public interface TaskChoiceMvpPresenter<V extends TaskChoiceMvpView> extends MvpPresenter<V> {


    void getGrammar(String topic_id);
}
