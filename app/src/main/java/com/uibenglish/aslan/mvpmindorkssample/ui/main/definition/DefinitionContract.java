package com.uibenglish.aslan.mvpmindorkssample.ui.main.definition;

import com.uibenglish.aslan.mvpmindorkssample.data.content.TranslationResponse;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpPresenter;
import com.uibenglish.aslan.mvpmindorkssample.ui.base.MvpView;

public interface DefinitionContract {

    interface DefinitionMvpView extends MvpView{

        void setWordDefinition(TranslationResponse response);
    }

    interface DefinitionMvpPresenter<V extends DefinitionContract.DefinitionMvpView> extends MvpPresenter<V>{

        void getWordDefinition(String word);

        void addToDictionaryAfterShow(String word);
    }
}
