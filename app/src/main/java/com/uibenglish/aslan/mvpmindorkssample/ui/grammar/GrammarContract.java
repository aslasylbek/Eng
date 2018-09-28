package com.uibenglish.aslan.mvpmindorkssample.ui.grammar;

import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Grammar;

import java.util.List;

public interface GrammarContract {

    interface GrammarMvpView extends MvpView{
        void setNewDataFromRoom(List<Grammar> grammarList);
        void addFinishFragment(int result);
    }

    interface GrammarMvpPresenter<V extends GrammarMvpView> extends MvpPresenter<V>{
        void requestGrammarCollection(String topic_id);
        void sendGrammarResult(String topicId, int result_ans, int result_cons, long start_time);

    }
}
