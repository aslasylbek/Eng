package com.example.aslan.mvpmindorkssample.ui.main.definition;

import com.example.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.example.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.example.aslan.mvpmindorkssample.ui.base.MvpView;

public interface DefinitionContract {

    interface DefinitionMvpView extends MvpView{

        void setWordDefinition(TranslationResponse response);
    }

    interface DefinitionMvpPresenter<V extends DefinitionContract.DefinitionMvpView> extends MvpPresenter<V>{

        void getWordDefinition(String word);

        void addToDictionaryAfterShow(String word);
    }
}
