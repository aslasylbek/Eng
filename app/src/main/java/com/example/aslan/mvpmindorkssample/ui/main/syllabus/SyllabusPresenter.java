package com.example.aslan.mvpmindorkssample.ui.main.syllabus;

import com.example.aslan.mvpmindorkssample.data.DataManager;
import com.example.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.example.aslan.mvpmindorkssample.ui.main.content.Topic;

import java.util.List;

public class SyllabusPresenter<V extends SyllabusMvpView> extends BasePresenter<V> implements SyllabusMvpPresenter<V> {


    public SyllabusPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void getTopicsInformation() {
        getMvpView().showLoading();
        List<Topic> topicList = getDataManager().getAllTopics();
        getMvpView().setTopicsData(topicList);
        getMvpView().hideLoading();
    }
}
