package com.uibenglish.aslan.mvpmindorkssample.ui.main.syllabus;

import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;

public interface SyllabusMvpPresenter<V extends SyllabusMvpView> extends MvpPresenter<V> {

    void getTopicsInformation();

}
