package com.uibenglish.aslan.mvpmindorkssample.ui.tasks;

import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.expandable.LessonChildItem;

public class TaskChoicePresenter<V extends TaskChoiceMvpView>extends BasePresenter<V> implements TaskChoiceMvpPresenter<V> {

    public TaskChoicePresenter(DataManager mDataManager) {
        super(mDataManager);
    }


    @Override
    public void testRequest() {
        /*getDataManager().requestForWordTranslate("hello", new DataManager.GetWordTranslation() {
            @Override
            public void onSuccess(TranslationResponse response) {
                Log.d("AAA", "onSuccess: "+response);
            }

            @Override
            public void onError() {
                Log.d("AAA", "onError: ");
            }
        });*/
    }
}
