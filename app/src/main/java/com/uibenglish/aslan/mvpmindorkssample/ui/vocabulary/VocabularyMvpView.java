package com.uibenglish.aslan.mvpmindorkssample.ui.vocabulary;

import com.uibenglish.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Word;

import java.util.List;

public interface VocabularyMvpView extends MvpView{

    void setData(List<Word> response);

    void addFinishFragment(int result);
}
