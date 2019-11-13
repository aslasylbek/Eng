package com.uibenglish.aslan.mvpmindorkssample.ui.main;

import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Info;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Topic;

import java.util.List;

public interface MainMvpView extends MvpView{

    //Set some content
    void setHeaderView(String name, String group, String program);

    void openSlashActivity();

    void startDeviceTokenRouting();


    List<Topic> setHolderData(List<Topic> topics);




}
