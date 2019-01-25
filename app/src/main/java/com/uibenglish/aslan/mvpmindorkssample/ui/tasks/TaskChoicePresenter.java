package com.uibenglish.aslan.mvpmindorkssample.ui.tasks;

import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.data.DataManager;
import com.uibenglish.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.uibenglish.aslan.mvpmindorkssample.data.remote.ApiFactory;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.BasePresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Grammar;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.expandable.LessonChildItem;

import java.util.List;

public class TaskChoicePresenter<V extends TaskChoiceMvpView>extends BasePresenter<V> implements TaskChoiceMvpPresenter<V> {

    public TaskChoicePresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void getGrammar(String topic_id){
        if (!isAttached())
            return;
        getMvpView().showLoading();
        getDataManager().getGrammarArray("366", new DataManager.GetGrammarListCallback() {
            @Override
            public void onSuccess(List<Grammar> grammarList) {
                if(grammarList.get(0).getMissword().size()>0&&grammarList.get(0).getConstructor().size()>0){
                    getMvpView().addGrammar(2);
                }
                else if (grammarList.get(0).getConstructor().size()>0){
                    getMvpView().addGrammar(1);
                }
                else if(grammarList.get(0).getMissword().size()>0){
                    getMvpView().addGrammar(0);
                }
                getMvpView().hideLoading();
            }

            @Override
            public void onError(Throwable t) {
                getMvpView().showSnackbar(t.getLocalizedMessage());
                getMvpView().hideLoading();
            }
        });
    }
}
