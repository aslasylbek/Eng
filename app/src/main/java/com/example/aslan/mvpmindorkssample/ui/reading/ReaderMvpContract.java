package com.example.aslan.mvpmindorkssample.ui.reading;

import com.example.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;

import java.util.ArrayList;

public interface ReaderMvpContract {

    interface ReaderMvpView extends MvpView{
        void setTranslate(String[] translates);

        void setTextFromDb(String textFromDb);
    }

    interface ReaderMvpPresenter<V extends ReaderMvpView> extends MvpPresenter<V>{

        void getWordTranslate(String word);

        void requestForLocalReadingText(String topicId);

    }
}
