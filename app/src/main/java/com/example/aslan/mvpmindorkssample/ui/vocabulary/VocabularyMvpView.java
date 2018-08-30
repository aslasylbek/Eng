package com.example.aslan.mvpmindorkssample.ui.vocabulary;

import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;
import com.example.aslan.mvpmindorkssample.ui.main.content.Word;

import java.util.List;

public interface VocabularyMvpView extends MvpView{

    void setData(List<Word> response);

    ResultContent sendExerciseData();

    void addFinishFragment(int result);
}
