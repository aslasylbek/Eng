package com.example.aslan.mvpmindorkssample.ui.grammar;

import com.example.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;
import com.example.aslan.mvpmindorkssample.ui.main.content.Grammar;

import java.util.List;

public interface GrammarContract {

    interface GrammarMvpView extends MvpView{
        void setNewDataFromRoom(List<Grammar> grammarList);
        void addFinishFragment();
    }

    interface GrammarMvpPresenter<V extends GrammarMvpView> extends MvpPresenter<V>{
        void getGrammarLocalData(String topic_id);
        void sendGrammarResult(String ex_id, String topicId, int result);
    }
}
