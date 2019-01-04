package com.uibenglish.aslan.mvpmindorkssample.ui.bbcenglish;

import com.uibenglish.aslan.mvpmindorkssample.data.models.BBCCategories;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;

import java.util.List;

public interface BBCContract {

    interface BBCMvpView extends MvpView{
        void setTabbarTitles(List<BBCCategories> categoriesList);
    }

    interface BBCMvpPresenter<V extends BBCMvpView> extends MvpPresenter<V>{
        void requestForBCCCategories();
    }
}
