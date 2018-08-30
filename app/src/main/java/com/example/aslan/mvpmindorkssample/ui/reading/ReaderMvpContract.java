package com.example.aslan.mvpmindorkssample.ui.reading;

import com.example.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;
import com.example.aslan.mvpmindorkssample.ui.main.content.Reading;

import java.util.ArrayList;
import java.util.List;

public interface ReaderMvpContract {

    interface ReaderMvpView extends MvpView{
        void setTranslate(String translates);

        void spreadTextToFragments(List<Reading> readingList);

        void addFinishFragment(int totalResult);

    }

    interface ReaderMvpPresenter<V extends ReaderMvpView> extends MvpPresenter<V>{

        void getWordTranslate(String word);

        void requestForLocalReadingText(String topicId);

        void addToDictionary(String word);

        void postResultToServer(String topic_id, int result_tf, int result_ans, long startTime);
    }
}
