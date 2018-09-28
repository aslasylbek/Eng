package com.uibenglish.aslan.mvpmindorkssample.ui.listening;

import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Listening;

import java.util.List;

public interface ListeningContract {
    interface ListeningMvpView extends MvpView{

        void spreadListeningCollection(List<Listening> collection);

        void addFinishFragment(int result);
    }

    interface ListeningMvpPresenter<V extends ListeningMvpView> extends MvpPresenter<V>{

        void requestForListeningCollection(String topic_id);

        void setListeningResult(String topic_id, int result_ans, long start_time);
    }
}
