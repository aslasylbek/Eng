package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish;

import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCLessonsList;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;

import java.util.List;

public class BBCListPresenter<V extends BBCListContract.BBCListMvpView> extends BasePresenter<V> implements BBCListContract.BBCListMvpPresenter<V> {

    public BBCListPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void requestLessonsById(int category_id) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().getBBCLessonList(category_id, new DataManager.GetBBCLessonList() {
                @Override
                public void onSuccess(List<BBCLessonsList.Lesson> bbcLessonsLists) {
                    getMvpView().reloadListData(bbcLessonsLists);
                    getMvpView().hideLoading();
                }

                @Override
                public void onError(Throwable t) {
                    getMvpView().hideLoading();
                }
            });
        }else getMvpView().noInternetConnection();
    }
}
