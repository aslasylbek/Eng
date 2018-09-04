package com.example.aslan.mvpmindorkssample.ui.main;

import com.example.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;

public interface MainMvpPresenter<V extends MvpView> extends MvpPresenter<V> {


    void setUserLogOut();
    void requestForStudentDiscipline();
    void requestForHeaderView();

    void sendDeviceToken();


}
