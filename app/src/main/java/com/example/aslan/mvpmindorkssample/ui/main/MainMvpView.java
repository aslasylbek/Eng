package com.example.aslan.mvpmindorkssample.ui.main;

import com.example.aslan.mvpmindorkssample.ui.base.MvpView;

public interface MainMvpView extends MvpView{

    //Set some content
    void setHeaderView();

    //Set menu from model
    void setNavigationView();

    void openSlashActivity();




}
