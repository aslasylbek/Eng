package com.uibenglish.aslan.mvpmindorkssample.ui.main.syllabus;

import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Topic;

import java.util.List;

public interface SyllabusMvpView extends MvpView{

    void setTopicsData(List<Topic> topicsData);

    void setOnErrorMessage();
}
