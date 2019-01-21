package com.uibenglish.aslan.mvpmindorkssample.ui.main;

import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Info;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Topic;

import java.util.List;

public interface MainMvpView extends MvpView{

    //Set some content
    void setHeaderView(String name, String group, String program);

    //Set menu from model
    void setNavigationView();

    void openSlashActivity();

    List<Topic> setHolderData(List<Topic> topics);




}
