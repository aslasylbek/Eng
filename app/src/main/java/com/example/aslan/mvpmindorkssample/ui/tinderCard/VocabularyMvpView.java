package com.example.aslan.mvpmindorkssample.ui.tinderCard;

import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;

public interface VocabularyMvpView extends MvpView{

    void setData(TranslationResponse response);
}
