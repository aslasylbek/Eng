package com.uibenglish.aslan.mvpmindorkssample.ui.splash;

import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;

/**
 * Created by aslan on 17.05.2018.
 */

public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void decideNextActivty();

}
