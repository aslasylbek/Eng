package com.uibenglish.aslan.mvpmindorkssample.ui.main;

import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;

public interface MainMvpPresenter<V extends MvpView> extends MvpPresenter<V> {


    void setUserLogOut();
    void requestForStudentDiscipline();
    void requestForHeaderView();

    void sendDeviceToken();


}
